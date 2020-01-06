package com.github.dagwud.woodlands.gson.adapter;

import com.github.dagwud.woodlands.gson.game.Root;
import org.junit.Test;

import static org.junit.Assert.*;

public class GsonHelperTest
{
  @Test
  public void testParse()
  {
    String json = "{\n" +
            "  \"Packages\": [\n" +
            "    {\n" +
            "      \"Name\": \"test\",\n" +
            "      \"actions\": [\n" +
            "        {\n" +
            "          \"name\": \"definitionofproc\",\n" +
            "          \"steps\": [\n" +
            "            {\n" +
            "              \"ProcName\": \"myproc\",\n" +
            "              \"ParamMappings\": {\n" +
            "                \"inpparam\": \"bla bla bla\"" +
            "              }\n," +
            "              \"OutputMappings\": {\n" +
            "                 \"someparam\": \"val\"\n" +
            "              }\n" +
            "            }\n" +
            "          ]\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    GsonHelper.readJSON(json, Root.class);
  }
}