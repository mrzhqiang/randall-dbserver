package randall.dbserver;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.ini4j.Ini;
import org.ini4j.Profile;
import org.ini4j.Wini;
import randall.common.ui.Dialogs;

/**
 * @author mrzhqiang
 */
public class DBShare {
  public boolean openDbBusy;

  private String dataDBFilePath = ".\\DB\\";

  private int serverPort = 6000;
  private String serverAddress = "0.0.0.0";
  private int gatePort = 5100;
  private String gateAddress = "0.0.0.0";
  private int idServerPort = 5600;
  private String idServerAddress = "127.0.0.1";
  public boolean testServer = true;

  private String serverName = "Randall";
  private String configFilename = ".\\Dbsrc.ini";
  private String configClass = "DBServer";
  private String serverIPConfigFilename = ".\\!addrtable.txt";
  private String heroDB = "HeroDB";
  private List<String> serverIPList = Lists.newArrayList();
  private boolean autoSort = true;
  private boolean sortClass = false;
  private int sortHour = 0;
  private int sortMinute = 4;
  private int sortMinLevel = 0;
  private int sortMaxLevel = 200;

  public int n4ADBFC;
  public int hackerNewChrCount;
  public int hackerDelChrCount;
  public int hackerSelChrCount;
  public int n4ADC1C;
  public int n4ADC20;
  public int n4ADC24;
  public int n4ADC28;

  public void loadConfig() {
    try {
      Ini config = new Wini(new File(configFilename));
      if (config.containsKey(configClass)) {
        Profile.Section section = config.get(configClass);
        serverName = section.get("ServerName", serverName);
        serverPort = section.get("ServerPort", Integer.class, serverPort);
        serverAddress = section.get("ServerAddr", serverAddress);
        gatePort = section.get("GatePort", Integer.class, gatePort);
        gateAddress = section.get("GateAddr", gateAddress);
        idServerAddress = section.get("IDSAddr", idServerAddress);
        idServerPort = section.get("IDSPort", Integer.class, idServerPort);
        heroDB = section.get("DBName", heroDB);
        dataDBFilePath = section.get("DBDir", dataDBFilePath);
        testServer = !section.get("NotRepeatName", Boolean.class, !testServer);
        autoSort = section.get("AutoSort", Boolean.class, autoSort);
        sortClass = section.get("SortClass", Boolean.class, sortClass);
        sortHour = section.get("SortHour", Integer.class, sortHour);
        sortMinute = section.get("SortMinute", Integer.class, sortMinute);
        sortMinLevel = section.get("SortMinLevel", Integer.class, sortMinLevel);
        sortMaxLevel = section.get("SortMaxLevel", Integer.class, sortMaxLevel);

        section.put("ServerName", serverName);
        section.put("ServerPort", serverPort);
        section.put("ServerAddr", serverAddress);
        section.put("GatePort", gatePort);
        section.put("GateAddr", gateAddress);
        section.put("IDSAddr", idServerAddress);
        section.put("IDSPort", idServerPort);
        section.put("DBName", heroDB);
        section.put("DBDir", dataDBFilePath);
        section.put("AutoSort", autoSort);
        section.put("SortClass", sortClass);
        section.put("SortHour", sortHour);
        section.put("SortMinute", sortMinute);
        section.put("SortMinLevel", sortMinLevel);
        section.put("SortMaxLevel", sortMaxLevel);
        section.put("NotRepeatName", !testServer);

        config.store();
      }
    } catch (IOException e) {
      Dialogs.error("无法加载数据库服务器配置文件！", e);
    }

    try {
      serverIPList = Files.readAllLines(Paths.get(serverIPConfigFilename));
    } catch (IOException e) {
      Dialogs.error("无法加载服务器 IP 列表配置文件！", e);
    }
  }
}
