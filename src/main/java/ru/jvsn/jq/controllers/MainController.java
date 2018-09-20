package ru.jvsn.jq.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import ru.jvsn.jq.dao.*;
import ru.jvsn.jq.objects.Answer;
import ru.jvsn.jq.objects.Question;
import ru.jvsn.jq.objects.User;

import java.net.URL;
import java.util.*;

public class MainController implements Initializable {
    @FXML
    VBox answersVBox;
    @FXML
    AnchorPane questionPane;
    @FXML
    Label questionLabel;
    @FXML
    Label watchProgress;
    @FXML
    VBox questionImgPane;

    private ToggleGroup group;
    private User user;
    private int allQuestionCounter = 0;
    private List<Question> qList;
    private int questionCounter = 0; // счетчик всех ответов (для вывода в форме)

    private FactoryDAO factory = FactoryDAO.getInstance();
    private UserDAO userDao = factory.getUserDAO();
    private QuestionDAO question = factory.getQuestionDAO();
    private AnswerDAO answer = factory.getAnswerDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = new User();
        //user.setName("ProXZ");
        user.setTrueAnswers(0);


        questionPane.setBackground(Background.EMPTY);
        questionPane.setStyle("-fx-background-color: gainsboro");
        //
        qList = question.getAllQuestions();
        // Считаем сколько всего вопросов в базе (для статистики)
        allQuestionCounter = qList.size();

        // Запускаем вопросы
        getTheAnswers();
    }

    private void getTheAnswers() {
        // Проверка на наличие вопросов (не кончились ли)
        if (qList.size() <= 0) {
            questionLabel.setText("Все вопросы закончились!");
            Alerts.getWinnerAlert(user, allQuestionCounter);
            // Пересоздаем нового пользователя для новой игры
            user = Alerts.getNewUser();
            // Чистим вопросы
            qList.clear();
            // Заполняем по новой вопросы
            qList = question.getAllQuestions();
            // Обнуляем статистику прогресса
            questionCounter = 0;
        }

        // Выводим статистику прогресса
        watchProgress.setText(questionCounter + " / " + allQuestionCounter);

        // очищаем поле с ответами
        answersVBox.getChildren().clear();
        questionImgPane.getChildren().clear();
        // генерируем рандомный вопрос
        int rmd = (int) (Math.random() * qList.size());
        // Разбиваем вопрос (поиск картинок кода)
        String imgarr[] = qList.get(rmd).getQuestion().split("##code##");
        // Пихаем вопрос в верхний лэйбл
        questionLabel.setText(imgarr[0]);
        // Вставляем пикчу если она есть
        if (imgarr.length > 1) {
            String imgset = imgarr[1].replace(" ", "");
            System.out.println(imgarr[1].trim());
            Image img = new Image("/img/code/" + imgarr[1].trim());
            ImageView imageView = new ImageView(img);
            ScrollPane scrollPane = new ScrollPane(imageView);
            questionImgPane.getChildren().addAll(imageView, scrollPane);
        }


        // Достаем ответы к этому вопросу
        List<Answer> answerForThisQlist = answer.getAllAnswerForQuestion(qList.get(rmd).getId());
        // инициализируем группу
        group = new ToggleGroup();

        // Генерим наши радиобаттоны с ответами
        List<RadioButton> listWithRadioButtonsToRandom = new ArrayList<>();
        // динамически генерируем ответы в radiobuttons
        for (int i = 0; i < answerForThisQlist.size(); i++) {
            RadioButton rb = new RadioButton();
            rb.setFont(new Font("Verdana", 12)); // Стиль наших ответов
            rb.setText(answerForThisQlist.get(i).getAnswer());
            rb.setId(answerForThisQlist.get(i).getId() + "");
            rb.setToggleGroup(group);
            listWithRadioButtonsToRandom.add(rb);
        }

        // Мешаем нашу коллекцию что бы варианты ответов перемешались а не шли друг за другом как в базе
        long rnd = System.nanoTime();
        Collections.shuffle(listWithRadioButtonsToRandom, new Random(rnd));
        // Отступы между кнопками
        answersVBox.setSpacing(5);
        // Добавляем варианты ответов на форму
        answersVBox.getChildren().addAll(listWithRadioButtonsToRandom);
        // Удаляем пройденный вопрос из списка
        qList.remove(rmd);
    }

    // Обработка нажатия кнопки ответа
    public void btnAnswerPressed(ActionEvent event) {
        // получаем выбранный ответ
        RadioButton rb = (RadioButton) group.getSelectedToggle();
        // Проверка выбран ли ответ
        if (rb == null) {
            return;
        }
        // Увеличиваем счетчик прогресса (не важно какой ответ)
        questionCounter++;

        // получаем по выбранному айди ответ
        Answer trueAnswer = answer.getTrueAnswerId(rb.getId());
        // сверяем kind ответа (если 1 - правильный / 2 - НЕправильный)
        if (trueAnswer.getKind() == 1) {
            // Выиграл!
            // идем дальше
            int userAnswersTMP = user.getTrueAnswers();
            user.setTrueAnswers(userAnswersTMP + 1);
            // DEBUG - remove this
            System.out.println("правильный ответ");
            System.out.println("User: " + user.getName());
            System.out.println("Miss: " + user.getMistakes());
            System.out.println("----");
            // DEBUG - remove this

            getTheAnswers();
        } else if (trueAnswer.getKind() == 0) {
            // проиграл
            int userMistakesTMP = user.getMistakes();
            user.setMistakes(userMistakesTMP + 1);
            // DEBUG - remove this
            System.out.println("НЕ правильный ответ");
            System.out.println("User: " + user.getName());
            System.out.println("Miss: " + user.getMistakes());
            System.out.println("----");
            // DEBUG - remove this
            getTheAnswers();
        }
    }

    public void btnEndTestPressed() {
        questionLabel.setText("Вы закончили тест!");
        Alerts.getLoserAlert(user, questionCounter);
        // Пересоздаем нового пользователя для новой игры
        user = Alerts.getNewUser();
        // Чистим вопросы
        qList.clear();
        // Заполняем по новой вопросы
        qList = question.getAllQuestions();
        // Обнуляем статистику прогресса
        questionCounter = 0;
        getTheAnswers();
    }
}
