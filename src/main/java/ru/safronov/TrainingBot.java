package ru.safronov;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup.ReplyKeyboardMarkupBuilder;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.safronov.model.Exercise;
import ru.safronov.util.ExerciseData;

@Component
@PropertySource("messages.properties")
public class TrainingBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

  private final TelegramClient telegramClient;
  private final List<Exercise> allExercises = ExerciseData.getSortExercises();
  @Autowired
  private Environment env;

  public TrainingBot() {
    this.telegramClient = new OkHttpTelegramClient(getBotToken());
  }

  @Override
  public String getBotToken() {
    return System.getenv().get("BOT_TOKEN");
  }

  @Override
  public LongPollingUpdateConsumer getUpdatesConsumer() {
    return this;
  }

  @Override
  public void consume(Update update) {

    if (update.hasMessage() && update.getMessage().hasText()) {
      String messageText = update.getMessage().getText();
      long chatId = update.getMessage().getChatId();
      String firstName = update.getMessage().getChat().getFirstName();
      String exerciseUrl;

      if (messageText.equals("/start")) {
        createExerciseLinks(chatId, firstName);
      }
      //if messageText equals one of exerciseName
      else if ((exerciseUrl = getExerciseUrl(allExercises, messageText)) != null) {
        sendMessage(chatId, exerciseUrl);
      } else {
        sendMessage(chatId, env.getProperty("unknown.command"));
      }
    }
  }

  private void sendMessage(long chatId, String messageText) {
    SendMessage message = SendMessage
        .builder()
        .chatId(chatId)
        .text(messageText)
        .build();

    try {
      telegramClient.execute(message);
    } catch (TelegramApiException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  private void createExerciseLinks(long chatId, String userName) {
    SendMessage message = SendMessage
        .builder()
        .chatId(chatId)
        .text(userName + " " + env.getProperty("choose.exercise"))
        .build();

    ReplyKeyboardMarkupBuilder<?, ?> builder = ReplyKeyboardMarkup.builder();
    for (Exercise exercise : allExercises) {
      builder = builder.keyboardRow(new KeyboardRow(exercise.getName()));
    }
    message.setReplyMarkup(builder.build());

    try {
      telegramClient.execute(message);
    } catch (TelegramApiException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  private String getExerciseUrl(List<Exercise> exercises, String exerciseName) {
    try {
      return exercises.stream()
          .filter(ex -> ex.getName().equals(exerciseName))
          .findAny()
          .orElseThrow().getUrl();
    } catch (NoSuchElementException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }
}
