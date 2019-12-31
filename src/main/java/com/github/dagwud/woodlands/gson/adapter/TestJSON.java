package com.github.dagwud.woodlands.gson.adapter;

public class TestJSON
{
  public static final String TEST = "{\n"+
          "  \"Packages\": [\n"+
          "    {\n"+
          "      \"Name\": \"Draft\",\n"+
          "      \"actions\": [\n"+
          "        {\n"+
          "          \"name\": \"GetPlayerAttribute\",\n"+
          "          \"inputs\": [\n"+
          "            \"AttributeName\"\n"+
          "          ],\n"+
          "          \"steps\": [\n"+
          "            {\n"+
          "              \"ProcName\": \"Native:ReadVar\",\n"+
          "              \"ParamMappings\": {\n"+
          "                \"VarSet\": \"Player\",\n"+
          "                \"VarName\": \"AttributeName\"\n"+
          "              },\n"+
          "              \"OutputMappings\": {\n"+
          "                \"VarValue\": \"AttributeValue\"\n"+
          "              }\n"+
          "            }\n"+
          "          ],\n"+
          "          \"outputs\": [\n"+
          "            \"AttributeValue\"\n"+
          "          ]\n"+
          "        },\n"+
          "        {\n"+
          "          \"name\": \"Echo\",\n"+
          "          \"inputs\": [\n"+
          "            \"Message\"\n"+
          "          ],\n"+
          "          \"steps\": [\n"+
          "            {\n"+
          "              \"ProcName\": \"Native:SendMessage\",\n"+
          "              \"ParamMappings\": {\n"+
          "                \"Message\": \"Message\"\n"+
          "              }\n"+
          "            }\n"+
          "          ]\n"+
          "        },\n"+
          "        {\n"+
          "          \"name\": \"ReadPlayerName\",\n"+
          "          \"steps\": [\n"+
          "            {\n"+
          "              \"ProcName\": \"Echo\",\n"+
          "              \"ParamMappings\": {\n"+
          "                \"Message\": \"By what name will you be known?\" \n" +
          "              }\n"+
          "            },\n"+
          "            {\n"+
          "              \"ProcName\": \"Native:ReadTextInput\",\n"+
          "              \"OutputMappings\": {\n"+
          "                \"CapturedText\": \"CharacterName\"\n"+
          "              }\n"+
          "            },\n"+
          "            {\n"+
          "              \"ProcName\": \"SetPlayerAttribute\",\n"+
          "              \"ParamMappings\": {\n"+
          "                \"AttributeName\": \"Name\",\n"+
          "                \"AttributeValue\": \"CharacterName\"\n"+
          "              }\n"+
          "            }\n"+
          "          ]\n"+
          "        },\n"+
          "        {\n"+
          "          \"name\": \"ReadPlayerClass\",\n"+
          "          \"steps\": [\n"+
          "            {\n"+
          "              \"ProcName\": \"Echo\",\n"+
          "              \"ParamMappings\": {\n"+
          "                \"Message\": \"Please choose your player class\" \n"+
          "              }\n"+
          "            },\n"+
          "            {\n"+
          "              \"ProcName\": \"ReadOption\",\n"+
          "              \"ParamMappings\": {\n"+
          "                \"OptionsCSV\": \"[Wizard,Druid,Explorer]\"\n"+
          "              },\n"+
          "              \"OutputMappings\": {\n"+
          "                \"ChosenOption\": \"Class\"\n"+
          "              }\n"+
          "            },\n"+
          "            {\n"+
          "              \"ProcName\": \"SetPlayerAttribute\",\n"+
          "              \"ParamMappings\": {\n"+
          "                \"AttributeName\": \"Class\",\n"+
          "                \"AttributeValue\": \"ChosenClass\"\n"+
          "              }\n"+
          "            }\n"+
          "          ]\n"+
          "        },\n"+
          "        {\n"+
          "          \"name\": \"ReadOption\",\n"+
          "          \"inputs\": [\n"+
          "            \"OptionsCSV\"\n"+
          "          ],\n"+
          "          \"steps\": [\n"+
          "            {\n"+
          "              \"ProcName\": \"Native:ReadOption\",\n"+
          "              \"ParamMappings\": {\n"+
          "                \"OptionsCSV\": \"OptionsCSV\"\n"+
          "              },\n"+
          "              \"OutputMappings\": {\n"+
          "                \"ChosenOption\": \"ChosenOption\"\n"+
          "              }\n"+
          "            }\n"+
          "          ],\n"+
          "          \"outputs\": [\n"+
          "            \"ChosenOption\"\n"+
          "          ]\n"+
          "        }\n"+
          "      ]\n"+
          "    }\n"+
          "  ]\n"+
          "}";
}
