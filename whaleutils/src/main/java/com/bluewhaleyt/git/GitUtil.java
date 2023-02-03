package com.bluewhaleyt.git;

import android.text.TextUtils;
import android.util.Log;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.io.PipedWriter;
import java.io.Writer;
import java.nio.file.FileAlreadyExistsException;
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

    public GitUtil(String localPath, String remotePath) {
        this.localPath = localPath;
        this.remotePath = remotePath;

        try {
            this.localRepo = new FileRepository(localPath + "/.git");
        } catch (IOException e) {
            e.printStackTrace();
        }

        credentialsProvider = new UsernamePasswordCredentialsProvider(this.username, this.password);
        git = new Git(localRepo);
    }

    public void cloneRepo() {
        try {
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
        } catch (GitAPIException e) {
            e.printStackTrace();
            Log.e(GitUtil.class.getName(), "Failed to clone git: " + remotePath);
        }
    }

    public void pullFromRepo(){
        try {
            git.pull().call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    public void addToRepo() {
        try {
            git.add().addFilepattern(".").call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    public void commitToRepo(String message) {
        try {
            git.commit().setMessage(message).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    public void pushToRepo(){
        git.push().setCredentialsProvider(credentialsProvider)
                .setForce(true)
                .setPushAll();
        Iterator<PushResult> it = null;

        try {
            it = git.push().call().iterator();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }

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
