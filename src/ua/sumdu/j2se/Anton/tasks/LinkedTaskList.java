package ua.sumdu.j2se.Anton.tasks;

import java.util.Objects;

/**
 * The concept of the task list does not depend on the task saving method;
 * the users of ArrayTaskList class objects may even be unaware of the way this class is implemented.
 * But the implementation through the array has its drawbacks, such as a slow operation of task deletion.
 * Therefore, for scenarios, in which task deletion often takes place,
 * it is necessary to create the list of tasks that will store tasks in a linked list
 * (single-linked, double-linked or another modification to choose from;
 * one cannot use the already existing implementations, such as java.util.LinkedList and others), which does not have this disadvantage.
 * Create the LinkedTaskList class in the same package with the same methods as ArrayTaskList
 * (in the incoming method change the object type that returns to LinkedTaskList).
 * The objects of this class should behave the same as the objects of ArrayTaskList class.
 * <p>
 * SOURCE : https://www.youtube.com/watch?v=BH6RJf2fVCQ&t=2232s&ab_channel=JavaVision
 */
public class LinkedTaskList<E> extends AbstractTaskList {
    private Node<E> firstNode;
    private Node<E> lastNode;
    private int size = 0;

    public LinkedTaskList() {
        lastNode = new Node<>(null, firstNode, null);
        firstNode = new Node<>(null, null, lastNode);
    }

    @Override
    public void add(Task task) {
        if (Objects.equals(task, null)) {
            throw new NullPointerException("The task was empty(null)!");
        }
        Node<E> previousElement = lastNode;
        previousElement.setCurrentElement((E) task);
        lastNode = new Node<>(null, previousElement, null);
        previousElement.setNextElement(lastNode);
        size++;
    }

    @Override
    public Task getTask(int index) {
     /*   if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException("index exceeds the permissible limits for the list!");
        }

      */
        Node<E> target = firstNode.getNextElement();
        for (int i = 0; i < index; i++) {
            target = target.getNextElement();
        }
        return (Task) target.getCurrentElement();
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean remove(Task task) {
        //LinkedTaskList<E> result = new LinkedTaskList();
        //не надо записывать лист. Реализовать логику линкедлиста. Перезаписать ссылки, не создавая новый объект.
        LinkedTaskList result = new LinkedTaskList();
        boolean isRemoved = false;
        for (int i = 0; i < size(); i++) {
            if (task.equals(getTask(i))) {
                isRemoved = true;
            } else {
                result.add(getTask(i));
            }
        }
        this.firstNode = result.firstNode;
        this.lastNode = result.lastNode;
        this.size = result.size;
        return isRemoved;
    }

    public boolean removeTest(Task task) {
        Node qwe = firstNode;

        while (qwe != null) {
            Node qwe1 = qwe.getNextElement();
            if (qwe1 != null && qwe1.currentElement != null && qwe1.currentElement.equals(task)) {
                qwe.setNextElement(qwe1.getNextElement());
                size--;
            }
            qwe = qwe1;
        }
        return false;
    }


    @Override
    public LinkedTaskList incoming(int from, int to) {
        if (to <= from || from < 0) {
            throw new IllegalArgumentException("incoming's interval was set as a wrong number's!");
        }
        LinkedTaskList<Task> result = new LinkedTaskList<>();
        for (int i = 0; i < size(); i++) {
            for (int j = from; j < to; j++) {
                if (getTask(i).nextTimeAfter(j) <= to && getTask(i).nextTimeAfter(j) != -1) {
                    result.add(getTask(i));
                    break;
                }
            }
        }
        return result;
    }

    //class of links;
    private class Node<E> {
        private E currentElement;
        private Node<E> nextElement;
        private Node<E> previousElement;

        private Node(E currentElement, Node<E> previousElement, Node<E> nextElement) {
            this.currentElement = currentElement;
            this.previousElement = previousElement;
            this.nextElement = nextElement;

        }

        //getters & setters:
        public E getCurrentElement() {
            return currentElement;
        }

        public void setCurrentElement(E currentElement) {
            this.currentElement = currentElement;
        }

        public Node<E> getNextElement() {
            return nextElement;
        }

        public void setNextElement(Node<E> nextElement) {
            this.nextElement = nextElement;
        }

        public Node<E> getPreviousElement() {
            return previousElement;
        }

        public void setPreviousElement(Node<E> previousElement) {
            this.previousElement = previousElement;
        }


    }

}