package ua.edu.sumdu.j2se.Bugaetc;

public class Main {

    public static void main(String[] args) {
        Task repetableTask = new Task("title", 12,20,3);
        System.out.println(repetableTask.nextTimeAfter(17));
    }
}
