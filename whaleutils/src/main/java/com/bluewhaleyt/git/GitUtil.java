package com.bluewhaleyt.git;

import android.util.Log;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class GitUtil {

    private String localPath, remotePath;
    private String username = "username", password = "password";
    private String token = "";
    private Git git;
    private Repository localRepo;
    private CredentialsProvider credentialsProvider;

    private String progressMessage;
    private int progress;

    public GitUtil(String localPath, String remotePath) throws IOException {
        this.localPath = localPath;
        this.remotePath = remotePath;
        this.localRepo = new FileRepository(localPath + "/.git");
        credentialsProvider = new UsernamePasswordCredentialsProvider(this.username, this.password);
        git = new Git(localRepo);
    }

    public String getLocalPath() {
        return localPath;
    }

    public String getRemotePath() {
        return remotePath;
    }

    public void cloneRepo() throws GitAPIException {
        Git.cloneRepository()
                .setURI(remotePath)
                .setDirectory(new File(localPath))
                .setProgressMonitor(new ProgressMonitor() {
                    @Override
                    public void start(int totalTasks) {
                        progress = totalTasks;
                    }

                    @Override
                    public void beginTask(String title, int totalWork) {
                        progressMessage = title;
                        progress = totalWork;
                    }

                    @Override
                    public void update(int completed) {

                    }

                    @Override
                    public void endTask() {
                    }

                    @Override
                    public boolean isCancelled() {
                        return false;
                    }
                })
                .call();
        Log.d(GitUtil.class.getName(), "Git cloned: " + remotePath);

    }

    public void pullFromRepo() throws GitAPIException {
        git.pull().call();
    }

    public void addToRepo() throws GitAPIException {
        git.add().addFilepattern(".").call();
    }

    public void commitToRepo(String message) throws GitAPIException {
        git.commit().setMessage(message).call();
    }

    public void pushToRepo() throws GitAPIException {
        git.push().setCredentialsProvider(credentialsProvider)
                .setForce(true)
                .setPushAll();
        Iterator<PushResult> it = null;

        it = git.push().call().iterator();

        if (it.hasNext()) {
            String str = it.next().toString();
        }
    }

    public int getProgress() {
        return progress;
    }

    public String getProgressMessage() {
        if (progressMessage != null) return progressMessage;
        return "Loading";
    }

}
