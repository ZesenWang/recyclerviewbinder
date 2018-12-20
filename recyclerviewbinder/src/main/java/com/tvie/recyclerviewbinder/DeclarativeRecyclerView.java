package com.tvie.recyclerviewbinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class DeclarativeRecyclerView extends RecyclerView {

    String namespace = "http://schemas.android.com/apk/res-auto";

    public DeclarativeRecyclerView(@NonNull Context context) {
        super(context);
    }

    public DeclarativeRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null)
            resolveAttributes(attrs, context);
    }

    public DeclarativeRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (attrs != null)
            resolveAttributes(attrs, context);
    }

    private void resolveAttributes(AttributeSet attrs, Context context) {
        String adapterClazz = attrs.getAttributeValue(namespace, "adapter");
        RecyclerView.Adapter adapter = null;
        if (adapterClazz != null)
            try {
                Class clazz = context.getClass();
                //Log.i(clazz.getField("TAG")., "resolveAttributes: ");
                Class nameClazz = Class.forName(adapterClazz);
                Constructor constructor = nameClazz.getConstructor(Context.class);
                adapter = (RecyclerView.Adapter) constructor.newInstance(context);
                setAdapter(adapter);
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
        String adapterListenerClazz = attrs.getAttributeValue(namespace, "adapter_listener");
        if (adapterListenerClazz != null && adapter != null) {
            try {
                Class nameClazz = Class.forName(adapterListenerClazz);
                Constructor constructor = nameClazz.getConstructor(Context.class);
                adapter = (RecyclerView.Adapter) constructor.newInstance(context);
                setAdapter(adapter);
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
        String layoutManagerClazz = attrs.getAttributeValue(namespace, "layout_manager");
        if (layoutManagerClazz != null) {
            if ("linearVertical".equals(layoutManagerClazz)) {
                setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            }
        }
        String itemDecorationEnum = attrs.getAttributeValue(namespace, "item_decoration");
        if (itemDecorationEnum != null) {
            if ("0".equals(itemDecorationEnum)) {
                addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
            }
        }
    }
}
