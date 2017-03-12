package taskexecutor;

import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/****
 * Parallel task executor
 */
public class ParallelTaskExecutor extends TaskExecutor {

    private ExecutorService executor;
    private LinkedList<Future<SimpleScoreResult>> list = new LinkedList<Future<SimpleScoreResult>>();

    public ParallelTaskExecutor() {
        int numberThreads = Runtime.getRuntime().availableProcessors();
        executor = Executors.newFixedThreadPool(numberThreads);
    }

    @Override
    public void addTask(SingleTask t) {
        synchronized (list) {
            Future<SimpleScoreResult> submit = executor.submit(new ParallelTaskCallable(t));
            list.add(submit);
        }
    }

    @Override
    public synchronized SimpleScoreResult getResult() {

        SimpleScoreResult obj = null;
        Future<SimpleScoreResult> callable;

        synchronized (list) {
            callable = list.pop();
        }
        try {
            obj = callable.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public void run() {
    }

    @Override
    public void stopTasks() {
        super.stopTasks();
        executor.shutdownNow();
    }

    private class ParallelTaskCallable implements Callable<SimpleScoreResult> {

        private SingleTask t;

        public ParallelTaskCallable(SingleTask t) {
            this.t = t;
        }

        @Override
        public SimpleScoreResult call() throws Exception {
            t.run();
            return t.getResult();
        }
    }
}