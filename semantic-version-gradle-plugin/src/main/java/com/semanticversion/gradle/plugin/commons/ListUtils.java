package com.semanticversion.gradle.plugin.commons;

import java.util.ArrayList;
import java.util.Collections;


@Deprecated
public class ListUtils {

	public static <E> ArrayList<E> newArrayList() {
		return new ArrayList<>();
	}

	public static <E> ArrayList<E> newArrayList(E... elements) {
		ArrayList<E> list = new ArrayList<>();
		Collections.addAll(list, elements);
		return list;
	}
}
