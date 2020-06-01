package com.xiaowei.part1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xiaowei
 * Lambda表达式是一个匿名方法，将行为像数据一样进行传递。
 */
public class TestMain {
    private static List<Student> mockData = new ArrayList<>();

    static {
        mockData.add(new Student(1, "x1"));
        mockData.add(new Student(2, "x2"));
        mockData.add(new Student(3, "x3"));
        mockData.add(new Student(4, "x4"));
        mockData.add(new Student(5, "x5"));

    }

    public static void main(String[] args) {
        // collect map filter min.max count
        testCompara();
        testReduce();
        refactorStream(null);
    }

    /**
     * 找出每个老师中id大于2的学生，不考虑重复
     */
    private static void refactorStream(List<Teacher> teachers) {
        // 普通的写法
        Set<String> stuNames = new HashSet<>();
        for (Teacher teacher : teachers) {
            for (Student student : teacher.getStudents()) {
                if (student.getId() > 2) {
                    String name = student.getName();
                    stuNames.add(name);
                }
            }
        }
        // 第二种写法
        Set<String> stuNames2 = new HashSet<>();
        teachers.stream().forEach(teacher -> {
            teacher.getStudents().stream().
                    filter(student -> student.getId() > 2).
                    map(Student::getName)
                    .forEach(name -> stuNames2.add(name));
        });
        // lambda写法
        // The flatMap() operation has the effect of applying a one-to-many transformation to the elements of the stream, and then flattening the resulting elements into a new stream.
        // 最优写法
        Set<String> stuNames3 = teachers.stream().flatMap(teacher -> teacher.getStudents().stream())
                .filter(student -> student.getId() > 2).map(Student::getName).collect(Collectors.toSet());
    }

    /**
     * 对比 普通 使用for循环查找最短曲目
     * min.max count 都属于 reduce操作
     */
    private static void testReduce() {
        // 初始化Student,最後最小的就是
        Student minStudent = mockData.get(0);
        for (Student student : mockData) {
            if (student.getId().compareTo(minStudent.getId()) < 0) {
                minStudent = student;
            }
        }
        // reduce的一个写法
        int count = Stream.of(1, 2, 3).reduce(0, (acc, element) -> acc + element);
        System.out.println(count == 6);
        // 展开reduce操作
        BinaryOperator<Integer> accumulator = (acc, element) -> acc + element;
        // ele acc  result
        // 1    0     1
        // 2    1     3
        // 3    3     6
        Integer apply = accumulator.apply(
                accumulator.apply(
                        accumulator.apply(
                                0, 1),
                        2),
                3);
        System.out.println(apply);
        // 第二种方式
        int acc = 0;
        for (Integer ele : Arrays.asList(1, 2, 3)) {
            acc = acc + ele;
        }
        System.out.println(acc);
    }

    /**
     * 比较器
     */
    private static void testCompara() {
        Student minStudent = mockData.stream().min(Comparator.comparing(stu -> stu.getId())).get();
        // Optional
        Student minStudent2 = mockData.stream().min(Comparator.comparing(Student::getId)).orElse(minStudent);
        System.out.println(minStudent);
    }

    /**
     * 集合的写法对比
     */
    private static void testFilterAndCount() {
        // 普通写法
        int count = 0;
        List<Student> studentList = new ArrayList<>(10);
        for (Student student : studentList) {
            // 可以被filter替代
            if (student.getName().startsWith("x")) {
                count++;
            }
        }
        // lambda写法，写法明确
        // 惰性求值和及早求值 建造者模式
        long x = studentList.stream().filter(p -> p.getName().startsWith("x")).count();
    }

    private static void testType() {
        // 某些情况下，用户需要手动指明类型，建议大家根据自己或项目组的习惯，采用让代码最便于阅读的方法
        // 虽然可以做到类型推断作用
        Map<String, String> dataMap = new HashMap<>(10);
        // java8可以省略类型信息
        // 检验大于5
        Predicate<Integer> atLeast = x -> x > 5;
        // 类型无法推断
        //Predicate least2 = (x) ->x > 5;
    }

    private static void testFinal() {
        // 匿名内部类
        final String name = "xiao";
        Button button = new Button();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("hi: " + name);
            }
        });
        // 函数式编程写法
        String id = "1";
        // 如果赋值报错
        id = "2";
        // 解决
        String finalId = id;
        // 此处换为 id 报错
        Runnable runnable = () -> System.out.println("hi " + finalId);
    }

    private static void test() {
        Runnable runnable = () -> System.out.println("item");
        // 复杂的写法
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("this is a actionListner");
            }
        };
        ActionListener actionListener2 = e -> System.out.println("this is a actionListner");
        // 1
        BinaryOperator<Long> binaryOperator1 = (x, y) -> x + y;
        // 2
        BinaryOperator<Long> binaryOperator2 = Long::sum;
    }

}
