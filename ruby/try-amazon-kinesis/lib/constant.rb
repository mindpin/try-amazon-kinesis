require 'inifile'

config_file_path = File.expand_path("../../client/sample.properties",__FILE__)
config = IniFile.load(config_file_path)["global"]
REGIN = config["regionName"]
STREAM_NAME = config["streamName"]
APPLICATION_NAME = config["applicationName"]
