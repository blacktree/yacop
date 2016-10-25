package org.apache.hadoop.yarn.applications.yacop.config;

import static org.junit.Assert.*;

import org.apache.hadoop.yarn.applications.yacop.config.BuilderException;
import org.apache.hadoop.yarn.applications.yacop.config.YacopConfig;
import org.apache.hadoop.yarn.applications.yacop.config.YacopConfigParser;
import org.codehaus.jettison.json.JSONException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Created by zyluo on 6/3/16.
 */
public class TestYacopConfigParser {

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @org.junit.Test
    public void parse() throws Exception {
        YacopConfig config = YacopConfigParser.parse(YacopConfigCorpus.readmeInput);
        String actual = config.toString();
        String expected = YacopConfigCorpus.readmeOutput;
        assertEquals(expected, actual);
    }

    //This volume mount feature will be reopen until YARN-3354 be merged.

    @Ignore
    @org.junit.Test
    public void testVolumeMountConfigParse() throws Exception {
        YacopConfig config = YacopConfigParser.parse(YacopConfigCorpus.testVolumeMountInput);
        String actual = config.toString();
        String expected = YacopConfigCorpus.testVolumeMountOutput;
        assertEquals(expected, actual);
    }

    @Ignore
    @org.junit.Test
    public void testVolumeMountErrorContainerPathInputParse() throws Exception {
        thrown.expect(BuilderException.class);
        thrown.expectMessage("illegal ContainerPath");
        YacopConfigParser.parse(YacopConfigCorpus.testVolumeMountErrorContainerPathInput);
    }

    @Ignore
    @org.junit.Test
    public void testVolumeMountErrorNoHostPathInputParse() throws Exception {
        thrown.expect(BuilderException.class);
        thrown.expectMessage("Invalid volume config");
        YacopConfigParser.parse(YacopConfigCorpus.testVolumeMountErrorNoHostPathInput);
    }
    
    //TODO: deal the test about these java code invoke shell script
    public void testNetworkConfigParse() throws Exception {
        YacopConfig config = YacopConfigParser.parse(YacopConfigCorpus.testNetworkInput);
        String actual = config.toString();
        String expected = YacopConfigCorpus.testNetworkOutput;
        assertEquals(expected, actual);  
    }

    @org.junit.Test
    public void testNetworkConfigParseWithEmptyName() throws Exception {
        thrown.expect(BuilderException.class);
        thrown.expectMessage("network cannot be empty");
    	YacopConfigParser.parse(YacopConfigCorpus.testNetworkInputWithEmptyName);
    }    

}