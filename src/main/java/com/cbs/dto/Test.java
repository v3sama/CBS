package com.cbs.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Chung
 */
@Getter
@Setter
public class Test {

    /**
     * @param args the command line arguments
     */
    static class Movie {

        public int length;
        public String title;

        public Movie(int length, String title) {
            this.length = length;
            this.title = title;
        }

        public Movie(Movie movie) {
            this.length = movie.length;
            this.title = movie.title;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    static class Room {

        public String code;

        public Room(String code) {
            this.code = code;
        }
    }

    static class Schedule {

        public List<Integer> values = new ArrayList<>();
        public List<String> titles = new ArrayList<>();

        private int time;
        public static int LAST;

        public Schedule(int first) {
            this.time = first;
        }

        public boolean canApply() {
            return time < LAST;
        }

        public void apply(Movie movie, int breakTime) {
            values.add(time);
            titles.add(movie.title);
            time += movie.length + breakTime;
        }

        private String toTime(int value) {
            return value / 60 + ":" + value % 60;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < values.size(); i++) {
                sb.append(toTime(values.get(i)))
                        .append('\t')
                        .append(titles.get(i))
                        .append('\n');
            }
//            this.values.forEach(v -> sb.append(toTime(v)).append('\n'));
            return sb.toString();
        }
    }

    public static void main(String[] args) {

        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie(105, "A"));
        movies.add(new Movie(120, "B"));
        movies.add(new Movie(92, "C"));

        int noOfMovies = movies.size();

        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("R1"));
        rooms.add(new Room("R2"));
        rooms.add(new Room("R3"));
        rooms.add(new Room("R4"));
        rooms.add(new Room("R5"));

        int noOfRooms = rooms.size();

        int last = 23 * 60;
        int first = 8 * 60;
        int breakTime = 15;
        int delay = 30;

        Schedule.LAST = last;
        int f = 0;
        for (int i = 0; i < noOfRooms; i++) {
            List<Movie> tmp = new ArrayList<>();
            for (int j = f; j < noOfMovies + f; j++) {
                tmp.add(new Movie(movies.get(j % noOfMovies)));
            }

            if (i > noOfMovies - 1) {
                first += delay;
            }

            System.out.println(tmp);

            newSchedule(first, breakTime, tmp);
            f++;
            
            first = 8 * 60;
        }
    }

    static void newSchedule(int first, int breakTime, List<Movie> movies) {
        Queue<Movie> queue = new ConcurrentLinkedQueue<>(movies);
        Schedule newSchedule = new Schedule(first);
        while (newSchedule.canApply()) {
            Movie tmp = queue.poll();
            newSchedule.apply(tmp, breakTime);
            queue.add(tmp);
        }

        System.out.println(newSchedule);
    }
}
