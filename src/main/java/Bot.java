import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    String wordForClass;
    int count = 10;

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new Bot());

        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }


    public void sendMsg(Message message, String text) { // Метод отвечает за отправку сообщения
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        //sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            if (count == 10) {
                setButtons(sendMessage);
            }
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    //TODO отправка фото
    public void sendPhoto(Message message, StringBuilder stringBuilder) {
        File soundFile1 = new File(String.valueOf(stringBuilder));
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(message.getChatId().toString());
        // sendPhoto.setReplyToMessageId(message.getMessageId()); // Ответ именно на определенное сообщение
        sendPhoto.setPhoto(new InputFile(soundFile1));
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

/*    public void sendMusic(Message message, String file) {
        File soundFile = new File(file); //Звуковой файл
        SendAudio sendAudio = new SendAudio();
        sendAudio.setChatId(message.getChatId().toString());
        sendAudio.setReplyToMessageId(message.getMessageId());
        sendAudio.setAudio(new InputFile(soundFile));
        try {
            execute(sendAudio);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }*/


    public void onUpdateReceived(Update update) { // Метод который слуашет сообщения в чате
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/start":
                    sendMsg(message, "Я бот помощник по автобусам");
                case "Новости❗":
                    sendMsg(message, "Привет новости на сегодня: закрыта улица республика 23");
                    break;
                case "Расписание автобуса\uD83D\uDE8D":
                    sendMsg(message, "Введите номер автобуса");
                    count = 1;
                    break;
                case "Маршрут автобуса\uD83D\uDCCD":
                    sendMsg(message, "Введите номер автобуса");
                    count = 2;
                    break;
                case "Все маршруты атобусов":
                    sendMsg(message, """
                            Городские маршруты:  1-25,28,29,31-37,39-41,44,46-48,50-54,56,60,61,64,70-73,80,81,120,701,4а,22а
                            Экспресс маршруты: 100-105
                            Пригородные маршруты: 300-322,326,801-808""");
                    break;
                case "Какие автобусы ходят на остановку":
                    sendMsg(message, "Введите название остановки");
                    count = 3;
                    break;
                default:
                    wordForClass = message.getText();
                    if (count == 1) {
                        MySql mySql = new MySql();
                        mySql.Sql(Integer.parseInt(wordForClass));
                        sendMsg(message, "Номер автобуса: " + mySql.getBusName() + " Расписание автобуса:");
                        sendPhoto(message, mySql.getBussTime());
                        count = 0;
                    } else if (count == 2) {
                        MySql mySql = new MySql();
                        mySql.Sql(Integer.parseInt(wordForClass));
                        sendMsg(message, "Номер автобуса: " + mySql.getBusName() + " Маршрут автобуса:");
                        sendPhoto(message, mySql.getBussMap());
                        count = 0;
                    } else if (count == 3) {
                        MySql mySql = new MySql();
                        mySql.Sql2(wordForClass);
                        sendMsg(message, " Номер автобуса: " + mySql.getStation());
                        mySql.setStation2();
                        count = 0;
                    }
            }
        }
    }


    public void setButtons(SendMessage sendMessage) { // Метод отвечает за кнопки
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardFirstRow2 = new KeyboardRow();


        keyboardFirstRow.add(new KeyboardButton("Новости❗"));
        keyboardFirstRow2.add(new KeyboardButton("Маршрут автобуса\uD83D\uDCCD"));
        keyboardFirstRow2.add(new KeyboardButton("Расписание автобуса\uD83D\uDE8D"));
        keyboardFirstRow2.add(new KeyboardButton("Какие автобусы ходят на остановку"));
        keyboardRowList.add(keyboardFirstRow);
        keyboardRowList.add(keyboardFirstRow2);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }


    @Override
    public String getBotUsername() {
        return "Marcho";
    }

    @Override
    public String getBotToken() {
        return "1762842300:AAErkUPPrTYvw6M2bsOEZYq-lLY8Xg1pd3s";
    }

}