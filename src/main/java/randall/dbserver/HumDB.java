package randall.dbserver;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import randall.common.util.mud.QuickIDList;
import randall.common.util.mud.QuickList;

/**
 * @author mrzhqiang
 */
public class HumDB {

  public FileHumDB humChrDB;
  public FileDB humDataDB;

  public static class FileHumDB {

    private final QuickList quickList;
    public final String dbFilename;
    private final QuickIDList quickIdList;
    private final List<String> deletedList;

    private Integer n4ADAFC;
    // hum count
    private Integer n4ADB04;

    public FileHumDB(String filename) {
      this.dbFilename = filename;
      this.quickList = new QuickList();
      this.quickIdList = new QuickIDList();
      this.deletedList = Lists.newArrayList();
      this.n4ADAFC = 0;
      this.n4ADB04 = 0;
    }
  }

  public static class FileDB {

  }
}
