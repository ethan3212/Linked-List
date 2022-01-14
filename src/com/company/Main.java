package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



class Link
{
    public long dData;      // data item
    public Link next;       // next Link in list
    public Link previous;   // previous Link in list
    //-----------------------------------------------------------------------------------------------------
    public Link(long dd)        // constructor
    {
        dData= dd;
    }   // end Link()
    //-----------------------------------------------------------------------------------------------------
    public void displayLink()       // display ourself
    {
        System.out.print(dData + " ");
    }   // end displayLink()
    //-----------------------------------------------------------------------------------------------------
}   // end class Link
///////////////////////////////////////////////////////////////////////////////////////////////////////////
class LinkList
{
    private Link first;     // ref to first item in list
    private Link last;      // ref to last item in list
    //-----------------------------------------------------------------------------------------------------
    public LinkList()       // constructor
    {
        first = null;       // no items in list yet
        last = null;
    }   // end LinkList()
    //-----------------------------------------------------------------------------------------------------
    public Link getFirst()      // get value of first
    {
        return first;
    }   // end getFirst()
    //-----------------------------------------------------------------------------------------------------
    public Link getLast()       // get value of last
    {
        return last;
    }   // end getLast()
    //-----------------------------------------------------------------------------------------------------
    public void setFirst(Link f)        // set first to new Link
    {
        first = f;
    }   // end setFirst()
    //-----------------------------------------------------------------------------------------------------
    public void setLast(Link l)       // set last to new Link
    {
        last = l;
    }   // end setLast()
    //-----------------------------------------------------------------------------------------------------
    public boolean isEmpty()        // true if list is empty
    {
        return (first == null);
    }   // end isEmpty()
    //-----------------------------------------------------------------------------------------------------
    public ListIterator getIterator()       // return iterator
    {
        return new ListIterator(this);      // initialized with this list
    }   // end getIterator()
    //-----------------------------------------------------------------------------------------------------
    public void displayList()
    {
        Link current = first;       // start at beginning of list
        while(current != null)      // until end of list
        {
            current.displayLink();  // print data
            current = current.next; // move to next link
        }   // end "while"
        System.out.println(" ");
    }   // end displayList()
    //-----------------------------------------------------------------------------------------------------
}   // end class LinkList
///////////////////////////////////////////////////////////////////////////////////////////////////////////
class ListIterator
{
    private Link current;       // current Link
    private Link previous;      // previous Link
    private Link next;          // next Link
    private LinkList ourList;   // our linked list
    //-----------------------------------------------------------------------------------------------------
    public ListIterator(LinkList list)      // constructor
    {
        ourList = list;
        reset();
    }   // end ListIterator()
    //-----------------------------------------------------------------------------------------------------
    public void reset()     // start at first
    {
        current = ourList.getFirst();
        previous = null;
    }   // end reset()
    //-----------------------------------------------------------------------------------------------------
    public boolean atEnd()      // true if last link
    {
        return (current.next == null);
    }   // end atEnd()
    //-----------------------------------------------------------------------------------------------------
    public void nextLink()      // go to next link
    {
        previous = current;
        current = current.next;
    }   // end nextLink()
    //-----------------------------------------------------------------------------------------------------
    public void previousLink()      // go to previous link
    {
        next = current;
        current = current.previous;
    }   // end previous
    //-----------------------------------------------------------------------------------------------------
    public Link getCurrent()        // get current link
    {
        return current;
    }   // end getCurrent()
    //-----------------------------------------------------------------------------------------------------
    public void insertAfter(long dd)        // insert after
    {
        Link newLink = new Link(dd);

        if(ourList.isEmpty())       // empty list
        {
            ourList.setFirst(newLink);
            current = newLink;
        }   // end "if"
        else        // not empty
        {
            newLink.next = current.next;
            current.next = newLink;
            nextLink();     // point to new link
        }   // end "else"
    }   // end insertAfter()
    //-----------------------------------------------------------------------------------------------------
    public void insertBefore(long dd)       // insert before current link
    {
        Link newLink = new Link(dd);

        if(previous == null)        // beginning of list
        {
            newLink.next = ourList.getFirst();
            ourList.setFirst(newLink);
            reset();
        }   // end "if"
        else        // not beginning
        {
            newLink.next = previous.next;
            previous.next = newLink;
            current = newLink;
        }   // end "else"
    }   // end insertBefore()
    //------------------------------------------------------------------------------------------------------
    public long deleteCurrent()     // delete item at current
    {
        long value = current.dData;
        if(previous == null)        // beginning of list
        {
            ourList.setFirst(current.next);
            reset();
        }   // end "if"
        else
        {
            previous.next = current.next;
            if(atEnd())
                reset();
            else
                current = current.next;
        }   // end "else"
        return value;
    }   // end deleteCurrent()
    //------------------------------------------------------------------------------------------------------
}   // end class ListIterator
////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class Main
{
    public static void main(String[] args) throws IOException {
        LinkList theList = new LinkList();      // new list
        ListIterator iter1 = theList.getIterator();     // new iter
        long value;

        iter1.insertAfter(20);      // insert items
        iter1.insertAfter(40);
        iter1.insertAfter(80);
        iter1.insertAfter(60);

        while(true)
        {
            System.out.print("Enter first letter of show, reset, ");
            System.out.print("next, get, before, after, delete: ");
            System.out.flush();
            int choice = getChar();     // get user's option
            switch(choice)
            {
                case 's':       // show list
                    if(!theList.isEmpty())
                        theList.displayList();
                    else
                        System.out.println("The list is empty");
                    break;
                case 'r':       // reset (to first)
                    iter1.reset();
                    break;
                case 'n':       // advance to next item
                    if(!theList.isEmpty() && !iter1.atEnd())
                        iter1.nextLink();
                    else
                        System.out.println("Can't go to next link");
                    break;
                case 'g':       // get current item
                    if(!theList.isEmpty())
                    {
                        value = iter1.getCurrent().dData;
                        System.out.println("Returned " + value);
                    }   // end "if"
                    else
                        System.out.println("List is empty");
                    break;
                case 'b':       // insert before current
                    System.out.print("Enter value to insert: ");
                    System.out.flush();
                    value = getInt();
                    iter1.insertBefore(value);
                    break;
                case 'a':       // insert after current
                    System.out.print("Enter value to insert: ");
                    System.out.flush();
                    value = getInt();
                    iter1.insertAfter(value);
                    break;
                case 'd':       // delete current item
                    if(!theList.isEmpty())
                    {
                        value = iter1.deleteCurrent();
                        System.out.println("Deleted " + value);
                    }   // end "if"
                    else
                        System.out.println("Can't delete");
                    break;
                default:
                    System.out.println("Invalid entry");
            }   // end "switch"
        }   // end "while"
    }   // end main()
    //-----------------------------------------------------------------------------------------------------------
    public static String getString() throws IOException
    {
        InputStreamReader isr = new InputStreamReader(System.in);

        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        return s;
    }   // end getString()
    //-----------------------------------------------------------------------------------------------------------
    public static char getChar() throws IOException
    {
        String s = getString();
        return s.charAt(0);
    }   // end getChar()
    //-----------------------------------------------------------------------------------------------------------
    public static int getInt() throws IOException
    {
        String s = getString();
        return Integer.parseInt(s);
    }   // end getInt()
    //-----------------------------------------------------------------------------------------------------------
}   // end class Main
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////