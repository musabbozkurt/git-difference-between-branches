package java.services;

import java.services.model.Commit;

import java.io.IOException;
import java.util.List;

import static java.services.model.Commit.doWork;
import static java.services.model.Commit.init;

public class CommitServiceImpl implements CommitService {
    private List<Commit> master;

    private List<Commit> stable;

    public CommitServiceImpl(List<Commit> master) {
        this.master = master;
    }

    public CommitServiceImpl(List<Commit> master, List<Commit> stable) {
        this.master = master;
        this.stable = stable;
    }

    public CommitServiceImpl(String author, String date) {

        try {
            master = init(Runtime.getRuntime().exec(prepareCommand("D:\\Dev\\code\\project-path-master\\project-path\\.git", author, date, null)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            stable = init(Runtime.getRuntime().exec(prepareCommand("D:\\Dev\\code\\project-path\\.git", author, date, null)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        if (!stable.isEmpty() && !master.isEmpty()) {
            System.out.println("Masterda olup Stable de olmayan Commitler");
            System.out.println("------------------------------------------");

            doWork(master, stable).stream().sorted((o1, o2) -> o2.getDate().compareTo(o1.getDate())).forEach(System.out::println);

            System.out.println("\nStablede olup Master'da olmayan Commitler");
            System.out.println("------------------------------------------");
            Commit.doWork(stable, master).stream().sorted((o1, o2) -> o2.getDate().compareTo(o1.getDate())).forEach(System.out::println);
        } else System.out.println("Master veya Stable commit bilgileri boş");

    }

    public List<Commit> getStable() {
        return stable;
    }

    public CommitServiceImpl setStable(List<Commit> stable) {
        this.stable = stable;
        return this;
    }

    public List<Commit> getMaster() {
        return master;
    }

    public CommitServiceImpl setMaster(List<Commit> master) {
        this.master = master;
        return this;
    }

    private String prepareCommand(String repoPath, String author, String after, String until) {
        return "git --git-dir=" + repoPath + " log --author=\"" + author + "\" --after=\"" + after + "\" --pretty=format:\"%h][%an][%ce][%ad][%s\" ";
    }
}


//Process stableProcess = Runtime.getRuntime().exec("git --git-dir=D:\\Dev\\code\\project-path\\.git log --author=\"akkan\" --after=\"2016-01-01\" --pretty=format:\"%h][%an][%ce][%ad][%s\" ");
//Process masterProcess = Runtime.getRuntime().exec("git --git-dir=D:\\Dev\\code\\project-path-master\\.git log --author=\"akkan\" --after=\"2016-01-01\" --pretty=format:\"%h][%an][%ce][%ad][%s\" ");
// git log --author="Musab" --pretty=format:"%h][%an][%ce][%ad][%s"
