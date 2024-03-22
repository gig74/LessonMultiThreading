package org.example.multithreading;

public class ThreadInteraction {
    boolean isResponse = false;

    public synchronized void methodRequest(String request){
        //System.out.println("methodRequest: " + isResponse);
        if(isResponse){
            try {
                System.out.println("methodRequest: " + "begin wait");
                wait();
                System.out.println("methodRequest: " + "end wait");
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("Request Pilot:");
        System.out.println(request);
        isResponse = true;
        System.out.println("methodRequest: " + "clear wait for Response");
        notify();
    }

    public synchronized void methodResponse(String response){
        //System.out.println("methodResponse: " + isResponse);
        if(!isResponse){
            try {
                System.out.println("methodResponse: " + "begin wait");
                wait();
                System.out.println("methodResponse: " + "end wait");
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        System.out.println("Response Controller:");
        System.out.println(response);
        isResponse = false;
        System.out.println("methodResponse: " + "clear wait for Request");
        notify();
    }
}
