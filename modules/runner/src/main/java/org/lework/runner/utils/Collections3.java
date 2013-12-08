package org.lework.runner.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Collections工具集.
 * 
 * 在JDK的Collections和Google.Guava的Collections2后, 命名为Collections3.
 * 
 * 函数主要由两部分组成，一是自反射提取元素的功能，二是源自Apache Commons Collection
 * 
 * @author calvin
 * @author Gongle
 */
public class Collections3 {
    /**
     * 获取List集合中目标成员的下一个成员
     * @param <T>
     * @param list
     * @param destination 目标成员
     */
    public static <T> T getNextElement(List<T> list ,T destination){          
        T result  = null ;
        if(!isEmpty(list)){
            return  list.get(getNextElementIndex(list, destination));
        }
        return result  ;
    }
    /**
     * 获取List集合中目标成员的下一个成员索引
     * @param <T>
     * @param list
     * @param destination   目标成员
     */
    public static <T> int getNextElementIndex(List<T> list ,T destination){    
        int m = -1 ;
        if(!isEmpty(list)){
            //我的位置索引
            m = list.indexOf(destination) ;
            //最后一个
            if(m == (list.size() -1) ){
                return  0 ;
            }else if(list.size() ==1 ){
                return  0 ;
            }else {
                return m +1 ;
            }
        }
        return -1  ;
    }
	/**
	 * 提取集合中的对象的两个属性(通过Getter函数), 组合成Map.
	 * 
	 * @param collection 来源集合.
	 * @param keyPropertyName 要提取为Map中的Key值的属性名.
	 * @param valuePropertyName 要提取为Map中的Value值的属性名.
	 */
	public static Map extractToMap(final Collection collection, final String keyPropertyName,
			final String valuePropertyName) {
		Map map = new HashMap(collection.size());

		try {
			for (Object obj : collection) {
				map.put(PropertyUtils.getProperty(obj, keyPropertyName),
						PropertyUtils.getProperty(obj, valuePropertyName));
			}
		} catch (Exception e) {
			throw Reflections.convertReflectionExceptionToUnchecked(e);
		}

		return map;
	}

	/**
	 * 提取集合中的对象的一个属性(通过Getter函数), 组合成List.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 */
	public static List extractToList(final Collection collection, final String propertyName) {
		List list = new ArrayList(collection.size());

		try {
			for (Object obj : collection) {
				list.add(PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (Exception e) {
			throw Reflections.convertReflectionExceptionToUnchecked(e);
		}

		return list;
	}
    /**
     * 提取集合中的对象的属性(通过Getter函数), 组合成List.
     *
     * @param collection 来源集合.
     * @param propertyName 要提取的属性名.
     * @param ignoreEmptyValue 是否过滤null值和""值
     *
     * @return List
     */
    public static <T> List<T> extractToList(Collection collection, String propertyName,boolean ignoreEmptyValue) {
        if (collection == null) {
            return null;
        }
        List list = new ArrayList();

        try {
            for (Object obj : collection) {
                T value = (T) PropertyUtils.getProperty(obj, propertyName);
                if (ignoreEmptyValue && value == null || value.toString().equals("")) {
                    continue;
                }
                list.add(PropertyUtils.getProperty(obj, propertyName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
	/**
	 * 提取集合中的对象的一个属性(通过Getter函数), 组合成由分割符分隔的字符串.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 * @param separator 分隔符.
	 */
	public static String extractToString(final Collection collection, final String propertyName, final String separator) {
		List list = extractToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

	/**
	 * 转换Collection所有元素(通过toString())为String, 中间以 separator分隔。
	 */
	public static String convertToString(final Collection collection, final String separator) {
		return StringUtils.join(collection, separator);
	}

	/**
	 * 转换Collection所有元素(通过toString())为String, 每个元素的前面加入prefix，后面加入postfix，如<div>mymessage</div>。
	 */
	public static String convertToString(final Collection collection, final String prefix, final String postfix) {
		StringBuilder builder = new StringBuilder();
		for (Object o : collection) {
			builder.append(prefix).append(o).append(postfix);
		}
		return builder.toString();
	}

	/**
	 * 判断是否为空.
	 */
	public static boolean isEmpty(Collection collection) {
		return ((collection == null) || collection.isEmpty());
	}

	/**
	 * 判断是否为空.
	 */
	public static boolean isNotEmpty(Collection collection) {
		return ((collection != null) && !(collection.isEmpty()));
	}

	/**
	 * 取得Collection的第一个元素，如果collection为空返回null.
	 */
	public static <T> T getFirst(Collection<T> collection) {
		if (isEmpty(collection)) {
			return null;
		}

		return collection.iterator().next();
	}

	/**
	 * 获取Collection的最后一个元素 ，如果collection为空返回null.
	 */
	public static <T> T getLast(Collection<T> collection) {
		if (isEmpty(collection)) {
			return null;
		}

		// 当类型为List时，直接取得最后一个元素 。
		if (collection instanceof List) {
			List<T> list = (List<T>) collection;
			return list.get(list.size() - 1);
		}

		// 其他类型通过iterator滚动到最后一个元素.
		Iterator<T> iterator = collection.iterator();
		while (true) {
			T current = iterator.next();
			if (!iterator.hasNext()) {
				return current;
			}
		}
	}

	/**
	 * 返回a+b的新List.
	 */
	public static <T> List<T> union(final Collection<T> a, final Collection<T> b) {
		List<T> result = new ArrayList<T>(a);
		result.addAll(b);
		return result;
	}

	/**
	 * 返回a-b的新List.
	 */
	public static <T> List<T> subtract(final Collection<T> a, final Collection<T> b) {
		List<T> list = new ArrayList<T>(a);
		for (T element : b) {
			list.remove(element);
		}

		return list;
	}

	/**
	 * 返回a与b的交集的新List.
	 */
	public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
		List<T> list = new ArrayList<T>();

		for (T element : a) {
			if (b.contains(element)) {
				list.add(element);
			}
		}
		return list;
	}
    /**
     * Adds all elements in the array to the given collection.
     *
     * @param collection  the collection to add to, must not be null
     * @param elements  the array of elements to add, must not be null
     * @throws NullPointerException if the collection or array is null
     */
    public static void addAll(Collection collection, Object[] elements) {
        for (int i = 0, size = elements.length; i < size; i++) {
            collection.add(elements[i]);
        }
    }

    /**
     * 元素是否包含于集合
     *
     * @param collection
     * @param element
     * @param <T>
     * @return
     */
    public static <T> boolean contain(Collection<T> collection, T element) {
        if (isEmpty(collection))
            return false;
        return collection.contains(element);
    }

}
