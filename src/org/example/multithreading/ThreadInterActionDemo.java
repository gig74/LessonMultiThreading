package org.example.multithreading;

public class ThreadInterActionDemo {
    public static void main(String[] args) {
        ThreadInteraction interaction = new ThreadInteraction();

        Request request = new Request(interaction);
        Response response = new Response(interaction);

    }
}