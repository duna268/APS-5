/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Daniel
 */
public class teste {
    public static void main(String[] args){
        Timer timer = new Timer();
        timer.schedule(new VerificaMensagemNova(), 0, 5000);
    }
}

class VerificaMensagemNova extends TimerTask {
    public void run() {
       System.out.println("Hello World!"); 
    }
}