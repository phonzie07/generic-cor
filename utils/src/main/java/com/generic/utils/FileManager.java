package com.generic.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.SwingUtilities;

public class FileManager {
    private static FileManager INSTANCE;
    private File file;
    private String TO_STRING;
    
    public FileManager createFile(String filePath, InputStream inputStream) throws IOException {
        File file = new File(filePath);
        //System.out.println("Directory: " + file.getParentFile());
        if(!file.exists()) {
            //System.out.println(file.getName() + " doesn't exist");
            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        /*else {
            System.out.println(file.getName() + " already exist");
        }*/
        return this;
    }
    
    public URI loadAsUri(String filePath) {
        File file = new File(filePath);
        return file.toURI();
    }
    /**
     * Method
     */
    // Add and Remove
    private String removeSpecialCharacters(String text) {
        String escapedData = text.replaceAll("\\R", " ");
        if(text.contains(",") || text.contains("\"") || text.contains("'")) {
            text = text.replace("\"", "\"\"");
            escapedData = "\"" + text + "\"";
        }
        return escapedData;
    }
    
    // Create and Delete
    public File createCSV(String[] headers) throws IOException {
        //file = File.createTempFile("temporary", ".csv");
        String filePath = "/var/tmp/temporary.csv";
        file = new File(filePath);
        String header = convertToCSV(headers) + System.lineSeparator();
        String[] data = new String[headers.length];
        Stream.of(data).map(text -> new String()).collect(Collectors.toList()).toArray(data);
        String text = header + convertToCSV(data);
        
        StringToOutputStream stos = new StringToOutputStream();
        stos.execute(text);
        
        return file;
    }
    
    public File createCSV(String[] headers, String[][] data) throws IOException {
        //file = File.createTempFile("temporary", ".csv");
        String filePath = "/var/tmp/temporary.csv";
        file = new File(filePath);
        String header = convertToCSV(headers) + System.lineSeparator();
        String newData = Stream.of(data).map(this::convertToCSV).collect(Collectors.joining(System.lineSeparator()));
        String text = header + newData;
        
        StringToOutputStream stos = new StringToOutputStream();
        stos.execute(text);
        
        return file;
    }
    
    public boolean deleteFile() {
        return file.delete();
    }
    
    // Converter
    private String convertToCSV(String[] lines) {
        return Stream.of(lines).map(this::removeSpecialCharacters).collect(Collectors.joining(","));
    }
    
    // Getter and Setter
    public static FileManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new FileManager();
        }
        return INSTANCE;
    }
    
    // Override
    @Override
    public String toString() {
        return TO_STRING;
    }
    
    // Read and Write
    public FileManager readCsv(String filePath) throws FileNotFoundException {
        InputStreamToString ists = new InputStreamToString();
        ists.execute(new FileInputStream(filePath));
        return this;
    }
    
    public String[] readLine(String text) {
        return readLine(text, 0);
    }
    
    public String[] readLine(String text, int offset) {
        return readLine(text, System.lineSeparator(), offset);
    }
    
    public String[] readLine(String text, String regex) {
        return readLine(text, regex, 0);
    }
    
    private String[] readLine(String text, String regex, int offset) {
        if(!text.isEmpty()) {
            String[] src = text.split(regex);
            String[] dest = new String[src.length - offset];
            AtomicInteger ctr = new AtomicInteger();
            Stream.of(src).map(String::trim).forEach(line -> {
                if(ctr.get() >= offset) {
                    dest[ctr.get() - offset] = line;
                }
                ctr.addAndGet(1);
            });
            
            return dest;
            //return Stream.of(src).map(String::trim).collect(Collectors.toList()).toArray(src);
            //return Stream.of(src).map(String::trim).collect(Collectors.toList());
        }
        
        return null;
    }
    
    /**
     * Abstract Class
     */
    // AsyncTask
    private abstract class AsyncTask<Params, Progress, Result> {
        protected AsyncTask() {
        
        }
        
        protected abstract void onPreExecute();
        
        protected abstract Result doInBackground(Params... params);
        
        protected abstract void onProgressUpdate(Progress... progress);
        
        protected abstract void onPostExecute(Result result);
        
        protected final void publishProgress(Progress... values) {
            SwingUtilities.invokeLater(() -> this.onProgressUpdate(values));
        }
        
        public final AsyncTask<Params, Progress, Result> execute(Params... params) {
            // Invoke pre execute
            try {
                SwingUtilities.invokeAndWait(this::onPreExecute);
            }
            catch(InvocationTargetException | InterruptedException e) {
                e.printStackTrace();
            }
            
            Buffer buffer = new Buffer();
            Thread tProducer = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        buffer.produce(params);
                    }
                    catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            Thread tConsumer = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        buffer.consume();
                    }
                    catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            try {
                // Invoke doInBackground
                tProducer.start();
                // Invoke onPostExecute
                tConsumer.start();
                tProducer.join();
                tConsumer.join();
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
            
            // Invoke doInBackground
            //CompletableFuture<Result> cf = CompletableFuture.supplyAsync(() -> doInBackground(params));
            
            // Invoke onPostExecute
            //cf.thenAccept(this::onPostExecute);
            return this;
        }
        
        /**
         * Source:
         * The Evolution of the Producer-Consumer Problem in Java
         * https://dzone.com/articles/the-evolution-of-producer-consumer-problem-in-java
         */
        private class Buffer {
            private Queue<Result> results;
            private int size;
            
            public Buffer() {
                results = new LinkedList<>();
                size = 1;
            }
            
            public void produce(Params... params) throws InterruptedException {
                while(true) {
                    synchronized(this) {
                        while(results.size() >= size) {
                            // Wait for the consumer
                            //System.out.println("Consumer Waiting...");
                            wait();
                        }
                        Result result = doInBackground(params);
                        results.add(result);
                        //System.out.print("Producer:\n" + result);
                        // Notify the consumer
                        notify();
                        Thread.sleep(1000);
                        //System.out.println("Producer Stop");
                        break;
                    }
                }
            }
            
            public void consume() throws InterruptedException {
                while(true) {
                    synchronized(this) {
                        while(results.size() == 0) {
                            // Wait for the producer
                            //System.out.println("Producer Waiting...");
                            wait();
                        }
                        Result result = results.poll();
                        onPostExecute(result);
                        //System.out.print("Consumer:\n" + result);
                        // Notify the producer
                        notify();
                        Thread.sleep(1000);
                        //System.out.println("Consumer Stop");
                        break;
                    }
                }
            }
        }
    }
    
    /**
     * Class
     */
    // Conversion
    private class InputStreamToString extends AsyncTask<InputStream, Integer, String> {
        @Override
        protected void onPreExecute() {
        
        }
        
        @Override
        protected String doInBackground(InputStream... inputStreams) {
            String text = "";
            try {
                InputStream fis = inputStreams[0];
                int read = 0;
                byte[] bytes = new byte[1024];
                for(;(read = fis.read(bytes)) != -1;) {
                    text += write(bytes, 0, read);
                }
                fis.close();
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
            
            return text;
        }
        
        @Override
        protected void onProgressUpdate(Integer... progress) {
        
        }
        
        @Override
        protected void onPostExecute(String string) {
            //System.out.println("Text: " + string);
            TO_STRING = string;
        }
        
        private String write(byte[] bytes, int offset, int length) {
            String text = "";
            for(;offset < length;offset++) {
                text += ((char) bytes[offset]);
            }
            return text;
        }
    }
    
    private class InputToOutputStream extends AsyncTask<InputStream, Integer, Void> {
        @Override
        protected void onPreExecute() {
        
        }
        
        @Override
        protected Void doInBackground(InputStream... inputStreams) {
            try {
                InputStream inputStream = inputStreams[0];
                OutputStream outputStream = null;
                if(inputStream instanceof FileInputStream) {
                    outputStream = new FileOutputStream(file);
                }
                int read = 0;
                byte[] bytes = new byte[1024];
                for(;(read = inputStream.read(bytes)) != -1;) {
                    outputStream.write(bytes, 0, read);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();
                
                /*File file = new File("C:\\Users\\user\\Desktop\\test\\output.txt");
                outputStream = new FileOutputStream(file);
                IOUtils.copy(is, outputStream);*/
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
            
            return null;
        }
        
        @Override
        protected void onProgressUpdate(Integer... progress) {
        
        }
        
        @Override
        protected void onPostExecute(Void aVoid) {
        
        }
    }
    
    private class StringToOutputStream extends AsyncTask<String, Integer, Void> {
        @Override
        protected void onPreExecute() {
        
        }
        
        @Override
        protected Void doInBackground(String... strings) {
            try {
                String text = strings[0];
                OutputStream outputStream = new FileOutputStream(file);
                byte[] bytes = text.getBytes();
                outputStream.write(bytes);
                outputStream.close();
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
            
            return null;
        }
        
        @Override
        protected void onProgressUpdate(Integer... progress) {
        
        }
        
        @Override
        protected void onPostExecute(Void aVoid) {
        
        }
    }
    
    // Recognizer method
    public boolean isCSVFile(String fileName) {
        return readFileExtension(fileName).equalsIgnoreCase("csv");
    }
    
    private String readFileExtension(String fileName) {
        Pattern p = Pattern.compile("[A-Za-z_0-9\\s]+[.pdf|.csv]$");
        Matcher m = p.matcher(fileName);
        String extension = fileName.substring(fileName.length() - 3);
        for(;m.find();) {
            //System.out.println(m.start() + ", " + m.end() + ", " + m.group());
            extension = m.group();
        }
        
        return extension;
    }
}
