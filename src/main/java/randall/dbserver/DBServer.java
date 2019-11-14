package randall.dbserver;

import java.net.URL;
import java.util.Arrays;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import randall.dbserver.util.Dialogs;
import randall.dbserver.util.Monitor;

/**
 * @author mrzhqiang
 */
public final class DBServer extends Application {
  public static final int DB_SERVER_PROCESS_CODE = 1001;

  public static void main(String[] args) {
    System.out.println(Arrays.toString(args));
    // todo 返回消息给游戏控制台
    launch(args);
  }

  private static final String APP_TITLE = "数据库服务器";
  private static final URL APP_FXML = DBServer.class.getResource("application.fxml");
  private static final URL APP_CSS = DBServer.class.getResource("application.css");

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle(APP_TITLE);
    Monitor monitor = Monitor.getInstance();
    monitor.record("准备启动程序..");
    try {
      FXMLLoader loader = new FXMLLoader(APP_FXML);
      Scene scene = new Scene(loader.load());
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
    } catch (Exception e) {
      e.printStackTrace();
    }
    monitor.report("程序启动完毕！");
  }

  @FXML void initialize() {

  }

  @Override
  public void stop() {
  }
}
