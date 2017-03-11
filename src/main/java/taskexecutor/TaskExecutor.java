package taskexecutor;


public abstract class TaskExecutor extends Thread {


    public abstract void addTask(SingleTask t);

    public abstract SimpleScoreResult getResult();

    public void setTotalNumberOfTasks(int nTasks) {
    }

    public void setDescription(String desc) {
    }

    public void run() {
    }

    public void stopTasks() {
    }


}