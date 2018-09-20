package ru.jvsn.jq.dao;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ru.jvsn.jq.objects.User;

import java.util.Optional;

public class Alerts {
    private static User user;

    public static void getWinnerAlert(User user, int qCounter){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Image img = new Image("/img/win.png");
        alert.setGraphic(new ImageView(img));
        alert.setTitle("Вы прошли тест!");
        alert.setHeaderText("ВЫ ПРОШЛИ ВСЕ ВОПРОСЫ!");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/img/win.png"));
        // формула процентов
        int proc = (user.getTrueAnswers() * 100) / qCounter;
        //
        String msg = "Поздравляем! \nВы ответили на все вопросы и даже остались живы!\n\n";
        msg += "Вы верно ответили на " + user.getTrueAnswers() + " вопросов из " + qCounter + " (" + proc + "% верных ответов) \n\n";
        alert.setContentText(msg);

        // CSS
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add("/css/alertstyle.css");
        dialogPane.getStyleClass().add("alertstyle");

        ButtonType buttonType1 = new ButtonType("Начать заново?");
        ButtonType buttonType2 = new ButtonType("Закрыть программу");

        alert.getButtonTypes().setAll(buttonType1, buttonType2);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonType1) {
            setNewUser(user);
        } else if (result.get() == buttonType2) {
            System.exit(0);
        }
    }

    public static void getLoserAlert(User user, int questionCounter) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Image img = new Image("/img/lose.png");
        alert.setGraphic(new ImageView(img));
        alert.setTitle("Вы закончили тест!");
        alert.setHeaderText("Вы закончили тестирование");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/img/lose.png"));
        // формула процентов
        int proc = (user.getTrueAnswers() * 100) / questionCounter;
        //
        String msg = "Вы досрочно закончили тестирование. \n\n";
        msg += "Вы прошли " + questionCounter + " вопросов. \n\n";
        msg += "Из них Вы верно ответили на " + user.getTrueAnswers() + " вопросов (" + proc + "% верных ответов) \n\n";
        alert.setContentText(msg);

        // CSS
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add("/css/alertstyle.css");
        dialogPane.getStyleClass().add("alertstyle");

        ButtonType buttonType1 = new ButtonType("Начать заново?");
        ButtonType buttonType2 = new ButtonType("Закрыть программу");
        alert.getButtonTypes().setAll(buttonType1, buttonType2);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonType1) {
            setNewUser(user);
        } else if (result.get() == buttonType2) {
            System.exit(0);
        }
    }

    private static void setNewUser(User oldUser) {
        user = new User();
        user.setName(oldUser.getName());
    }

    public static User getNewUser() {
        return user;
    }
}
