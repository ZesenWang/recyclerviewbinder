package com.tvie.recyclerviewbinder;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.support.v7.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class RecyclerViewBinder {
    private static RecyclerView sRecyclerView;
    private static Context sContext;

    public static void inject(Context context, int layoutId, RecyclerView recyclerView) {
        sRecyclerView = recyclerView;
        sContext = context;

        try (XmlResourceParser parser = context.getResources().getLayout(layoutId)) {
            int eventType = parser.getEventType();
            do {
                if (eventType == parser.START_DOCUMENT) {
                    System.out.println("Start document");
                } else if (eventType == parser.END_DOCUMENT) {
                    System.out.println("End document");
                } else if (eventType == parser.START_TAG) {
                    processStartElement(parser);
                } else if (eventType == parser.END_TAG) {
                    processEndElement(parser);
                } else if (eventType == parser.TEXT) {
                    processText(parser);
                }
                eventType = parser.next();
            } while (eventType != parser.END_DOCUMENT);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processText(XmlResourceParser parser) {

    }

    private static void processEndElement(XmlResourceParser parser) {

    }

    private static void processStartElement(XmlResourceParser parser) {
        String tagName = parser.getName();
        if (tagName.endsWith("RecyclerView"))
            for (int i = 0; i < parser.getAttributeCount(); i++) {
                //todo name don't have prefix, why?
                String name = parser.getAttributeName(i);
                System.out.println(name + "=" + parser.getAttributeValue(i));
                if ("adapter".equals(name)) {
                    setAdapter(parser.getAttributeValue(i));
                } else if ("adapter_listener".equals(name)) {

                } else if ("layout_manager".equals(name)) {

                } else if ("item_decoration".equals(name)) {

                }
            }
    }

    private static void setAdapter(String name) {
        try {
            Class nameClazz = Class.forName(name);
            Constructor constructor = nameClazz.getConstructor(Context.class);
            sRecyclerView.setAdapter((RecyclerView.Adapter) constructor.newInstance(sContext));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
