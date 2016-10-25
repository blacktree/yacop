package org.apache.hadoop.yarn.applications.yacop.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.yarn.applications.yacop.config.NetworkConfig.Builder;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class YacopConfigParser {

    public static YacopConfig parse(String body) throws JSONException, BuilderException {
        YacopConfig.Builder builder = new YacopConfig.Builder();
        JSONObject obj = new JSONObject(body);
        if (obj.has("name"))
            builder.name(obj.getString("name").trim());
        //TODO(zyluo): The builder has to read YARNConfig() to fill in defaults
        //             for missing values of cpus, mem -> parse(String body, YARNConfig defaults)
        if (obj.has("cpus"))
            builder.cpus(obj.getDouble("cpus"));
        if (obj.has("mem"))
            builder.mem(obj.getDouble("mem"));
        if (obj.has("instances"))
            builder.instances(obj.getInt("instances"));
        if (obj.has("cmd"))
            builder.cmd(obj.getString("cmd").trim());
        if (obj.has("args")) {
            JSONArray jsonArray = obj.getJSONArray("args");
            List<String> list = new ArrayList<>();
            for (int i=0; i<jsonArray.length(); i++) {
                String temp = jsonArray.getString(i).trim();
                if (!temp.isEmpty())
                    list.add(temp);
            }
            builder.args(list);
        }
        if (obj.has("engine")) {
            JSONObject engineObj = obj.getJSONObject("engine");
            EngineConfig engineConfig = null;
            try {
                engineConfig = parseEngineConfig(engineObj);
            } catch (JSONException e) {
                throw new BuilderException("Invalid volume config");
            }
            builder.engineConfig(engineConfig);
        }
        return builder.build();
    }

    private static EngineConfig parseEngineConfig(JSONObject obj) throws JSONException, BuilderException {
        EngineConfig.Builder engineBuilder = new EngineConfig.Builder();
        if (obj.has("type"))
            engineBuilder.type(obj.getString("type").trim());
        else
            engineBuilder.type("DOCKER");
        if (obj.has("image"))
            engineBuilder.image(obj.getString("image").trim());
        if (obj.has("localImage"))
            engineBuilder.localImage(obj.getBoolean("localImage"));

        //This volume mount feature will be reopen until YARN-3354 be merged.

//        if (obj.has("volumes")) {
//            JSONArray volumesObjArray = obj.getJSONArray("volumes");
//            for (int i = 0; i < volumesObjArray.length(); i++) {
//                JSONObject volumeObj = volumesObjArray.getJSONObject(i);
//                engineBuilder.addVolume(new VolumeConfig.Builder()
//                        .containerPath(volumeObj.getString("containerPath").trim())
//                        .hostPath(volumeObj.getString("hostPath").trim())
//                        .mode(volumeObj.getString("mode").trim()).build());
//            }
//        }
        if (obj.has("network")) {
            JSONObject networkObj = obj.getJSONObject("network");
            
            NetworkConfig.Builder networkConfigBuilder = new NetworkConfig.Builder()
                    .mode(networkObj.getString("mode").trim())
                    .name(networkObj.getString("name").trim());
            engineBuilder.addNetwork(networkConfigBuilder.build());
        }
        return engineBuilder.build();
    }
}
