package com.yangjun.baby.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BabyBaseAdapter<T> extends BaseAdapter{
	private static int DEFAULT_INIT_VALUE = -1;
	private static int DIGITAL_ZERO = 0;
	public Context mContext;
	private int mCount = DEFAULT_INIT_VALUE;
	public LayoutInflater mInflater;
	private LinkedList<T> mListItems;

	public BabyBaseAdapter(Context paramContext){
		this.mContext = paramContext;
		this.mInflater = LayoutInflater.from(paramContext);
		this.mListItems = new LinkedList<T>();
	}

	public void clear(){
		if (this.mListItems != null){
			this.mListItems.clear();
		}
	}

	public int getCount(){
		int count = DIGITAL_ZERO;
		if ((null!=this.mListItems) && (DIGITAL_ZERO!=this.mListItems.size())){
			count = this.mListItems.size();
		}
		return count;
	}

	public T getItem(int paramInt){
		if (isEmpty()){
			return null;
		}
		return this.mListItems.get(paramInt);
	}

	public long getItemId(int paramInt) {
		return paramInt;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup){
		return null;
	}

	public T removeData(int paramInt){
		T localObject = null;
		if (this.mListItems != null){
			localObject = this.mListItems.remove(paramInt);
		}
		return localObject;
	}

	public boolean removeData(T paramT){
		boolean bool = false;
		if (this.mListItems != null){
			bool = this.mListItems.remove(paramT);
		}
		return bool;
	}

	public T removeFirst(){
		T localObject = null;
		if (this.mListItems != null){
			localObject = this.mListItems.removeFirst();
		}
		return localObject;
	}

	public T removeLast(){
		T localObject = null;
		if (this.mListItems != null){
			localObject = this.mListItems.removeLast();
		}
		return localObject;
	}

	public void setData(T paramT){
		if (paramT != null){
			this.mListItems.add(paramT);
		}
  	}
	public void setDataPostion(int index,T paramT){
		if (paramT != null){
			this.mListItems.add(index,paramT);
		}
  	}
	public void setData(List<T> paramList){
		if (paramList != null){
			this.mListItems.addAll(paramList);
		}
	}

	public void setDataFirst(T paramT){
		if (paramT != null){
			this.mListItems.addFirst(paramT);
		}
	}

	public void setDataForEquals(List<T> paramList){
		Iterator<T> localIterator = null;
		if (paramList != null){
			localIterator = paramList.iterator();
		}
		while (true){
			if (!localIterator.hasNext()){
				return;
			}
			T localObject = (T)localIterator.next();
			if (!this.mListItems.contains(localObject)){
				this.mListItems.add(localObject);
			}
		}
	}

	public void setDataLast(T paramT){
		if (paramT != null){
			this.mListItems.addLast(paramT);
		}
	}

	public void setMultitermDataToFooter(LinkedList<T> paramLinkedList){
		if (paramLinkedList != null){
			this.mListItems.addAll(getCount(), paramLinkedList);
		}
	}

	public void setMultitermDataToFooter(List<T> paramList)
	{
		if (paramList != null){
			this.mListItems.addAll(getCount(), paramList);
		}
	}

	public void setMultitermDataToHader(LinkedList<T> paramLinkedList){
		if (paramLinkedList != null){
			this.mListItems.addAll(0, paramLinkedList);
		}
	}

	public void setMultitermDataToHader(List<T> paramList){
		if (paramList != null){
			this.mListItems.addAll(0, paramList);
		}
	}
}