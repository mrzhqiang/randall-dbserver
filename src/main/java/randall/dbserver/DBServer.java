package randall.dbserver;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import randall.common.Monitor;
import randall.common.ui.Dialogs;

/**
 * 数据库服务器。
 *
 * @author mrzhqiang
 */
public final class DBServer extends Application {
  private static final Logger LOGGER = LoggerFactory.getLogger("randall");

  public static void main(String[] args) {
    // fixme
    LOGGER.debug(Arrays.toString(args));
    launch(args);
  }

  public static final int DB_SERVER_PROCESS_CODE = 1001;

  private static final String APP_TITLE = "数据库服务器";
  private static final URL APP_FXML = DBServer.class.getResource("application.fxml");
  private static final URL APP_CSS = DBServer.class.getResource("application.css");

  private int accessCount;
  private List<String> serverList;
  private boolean remoteClose = false;

  private final DBShare share = new DBShare();
  private final HumDB humDB = new HumDB();

  private Timer startTimer = new Timer();

  @Override
  public void start(Stage primaryStage) {
    Monitor monitor = Monitor.getInstance();
    monitor.record("准备启动程序..");
    // run command "./DbServer.exe --width=1920" in CMD
    Parameters parameters = getParameters();
    String widthParam = parameters.getNamed().get("width");
    double width = Strings.isNullOrEmpty(widthParam) ? -1 : Double.parseDouble(widthParam);
    String heightParam = parameters.getNamed().get("height");
    double height = Strings.isNullOrEmpty(heightParam) ? -1 : Double.parseDouble(heightParam);
    try {
      primaryStage.setTitle(APP_TITLE);
      Scene scene = new Scene(FXMLLoader.load(APP_FXML), width, height);
      scene.getStylesheets().add(APP_CSS.toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.setOnCloseRequest(event -> {
        Dialogs.confirm("是否确认关闭数据库服务器？")
            .ifPresent(buttonType -> {
              stop();
              Platform.exit();
            });
      });
      primaryStage.show();
      monitor.report("程序启动完毕！");
    } catch (Exception e) {
      LOGGER.error("启动数据库服务器失败！", e);
      Platform.exit();
    }
  }

  @FXML
  public void initialize() {
    remoteClose = false;
    // todo send message to game center
    share.openDbBusy = true;
    humDB.humChrDB = null;
    humDB.humDataDB = null;
    share.loadConfig();
    serverList = Lists.newArrayList();
    share.n4ADBFC = 0;
    accessCount = 0;
    share.hackerNewChrCount = 0;
    share.hackerDelChrCount = 0;
    share.hackerSelChrCount = 0;
    share.n4ADC1C = 0;
    share.n4ADC20 = 0;
    share.n4ADC24 = 0;
    share.n4ADC28 = 0;
    // todo send message "正在启动数据库服务器..." to game center

    startTimer.schedule(new StartTimerTask(), 1000);
    if (!share.testServer) {
      // 数据库服务器 - 人物不重名
    } else {
      // 数据库服务器
    }
  }

  @Override
  public void stop() {
  }

  public class StartTimerTask extends TimerTask {
    @Override public void run() {
      cancel();
      // todo list view clear
      share.openDbBusy = true;

    }
  }

}
