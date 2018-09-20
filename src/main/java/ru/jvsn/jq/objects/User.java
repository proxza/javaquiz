package ru.jvsn.jq.objects;

public class User {
    private int id;
    private String name;
    private int mistakes;
    private int trueAnswers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMistakes() {
        return mistakes;
    }

    public void setMistakes(int mistakes) {
        this.mistakes = mistakes;
    }

    public int getTrueAnswers() {
        return trueAnswers;
    }

    public void setTrueAnswers(int trueAnswers) {
        this.trueAnswers = trueAnswers;
    }
}
