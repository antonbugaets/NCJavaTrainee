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
    // private int size = 0;

    public LinkedTaskList() {
        lastNode = new Node<>(null, firstNode, null);
        firstNode = new Node<>(null, null, lastNode);
        whoIsChild = "LinkedTaskList";
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
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException("index exceeds the permissible limits for the list!");
        }
        Node<E> target = firstNode.getNextElement();
        for (int i = 0; i < index; i++) {
            target = getNextElement(target);
        }
        return (Task) target.getCurrentElement();
    }


    private Node<E> getNextElement(Node<E> currentElement) {
        return currentElement.getNextElement();
    }

    @Override
    public boolean remove(Task task) {
        boolean isSuccess = false;
        Node firstLink = firstNode;
        while (firstLink != null && firstLink.getCurrentElement() != task) {
            boolean isRemoved = false;
            Node secondLink = firstLink.getNextElement();
            if (secondLink != null && secondLink.currentElement != null && secondLink.currentElement.equals(task)) {
                firstLink.setNextElement(secondLink.getNextElement());
                firstLink.setPreviousElement(secondLink.getPreviousElement());
                isRemoved = true;
                isSuccess = true;
                size--;
            }
            if (!isRemoved) {
                firstLink = secondLink;
            }
        }
        return isSuccess;
    }
/*
    @Override
    public LinkedTaskList incoming(int from, int to) {
        if (to <= from || from < 0) {
            throw new IllegalArgumentException("incoming's interval was set as a wrong number's!");
        }
        LinkedTaskList result = new LinkedTaskList<>();
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

 */


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