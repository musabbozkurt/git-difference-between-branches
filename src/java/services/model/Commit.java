package java.services.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Commit {
    private int id;
    private String hash;
    private Date date;
    private User user;
    private String message;

    public Commit() {
    }

    public Commit(int id, String line) {
        try {
            String[] delims = line.split("]\\[");

            hash = delims[0];
            user = new User(delims);
            // Sat Feb 23 11:24:39 2019
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM d HH:mm:ss yyyy Z", Locale.US);
            date = Date.from(LocalDateTime.parse(delims[3], formatter).atZone(ZoneId.systemDefault()).toInstant());

            this.id = id;
            if (delims.length == 5)
                this.message = delims[4];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Commit> init(Process process) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        List<Commit> commits = new ArrayList<>();
        AtomicInteger i = new AtomicInteger();
        bufferedReader.lines().forEach(s -> commits.add(new Commit(i.incrementAndGet(), s)));
        return commits;
    }

    public static List<Commit> init(String path) {
        List<Commit> commits = new ArrayList<>();
        AtomicInteger i = new AtomicInteger();
        try (Stream<String> stream = Files.lines(Paths.get(path), Charset.forName("Cp1252"))) {
            stream.forEach(s -> {
                if (!s.isEmpty()) {
                    commits.add(new Commit(i.incrementAndGet(), s));
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commits;
    }

    public static List<Commit> doWork(List<Commit> master, List<Commit> stable) {
        return master.parallelStream().filter(commit -> !commit.contains(stable, commit)).collect(Collectors.toList());
    }

    public boolean contains(List<Commit> commits, Commit commit) {
        return commits.parallelStream().anyMatch(commitOne -> commitOne.getMessage().equalsIgnoreCase(commit.getMessage()));
    }

    public String getMessage() {
        if (message != null && !message.isEmpty()) {
            return message.replaceAll("\\s+", "");
        }
        return String.valueOf("");
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return simpleDateFormat.format(date) + "\t" + user.getEmail() + "\t" + message;
    }

    public String getHash() {
        return hash;
    }

    public Commit setHash(String hash) {
        this.hash = hash;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Commit setDate(Date date) {
        this.date = date;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Commit setUser(User user) {
        this.user = user;
        return this;
    }
}
