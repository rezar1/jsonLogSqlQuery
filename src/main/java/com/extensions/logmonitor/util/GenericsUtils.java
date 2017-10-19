package com.extensions.logmonitor.util;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title GenericsUtils
 * @desc 一些通用的工具方法
 * @author Rezar
 * @date 2015年9月13日
 * @version 1.0
 */
@Slf4j
public class GenericsUtils {

	/**
	 * 判断某个对象是否为空或者如果是某些特殊对象的话，判断这些特殊对象的长度属性，是否为Empty
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean notNullAndEmpty(Object obj) {
		if (obj == null) {
			return false;
		} else if (obj instanceof String) {
			return ((String) obj).length() == 0 ? false : true;
		} else if (obj instanceof Collection<?>) {
			return ((Collection<?>) obj).size() == 0 ? false : true;
		} else if (obj instanceof Map<?, ?>) {
			return ((Map<?, ?>) obj).size() == 0 ? false : true;
		} else if (obj instanceof StringBuilder) {
			return ((StringBuilder) obj).length() == 0 ? false : true;
		} else if (obj instanceof StringBuffer) {
			return ((StringBuffer) obj).length() == 0 ? false : true;
		} else {
			Class<?> cla = obj.getClass();
			if (cla.isArray()) {
				return Array.getLength(obj) == 0 ? false : true;
			}
		}
		return true;
	}

	public static boolean isNullOrEmpty(Object obj) {
		return !notNullAndEmpty(obj);
	}

	public static boolean isArrays(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Collection<?> || (obj.getClass().isArray())) {
			return true;
		}
		return false;
	}

	/**
	 * 判断一个对象中的某个属性是否为普通的属性，且判断该属性是否为默认值
	 * 
	 * @param field
	 * @param qm
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static boolean isNotNull(Field field, Object qm) throws IllegalArgumentException, IllegalAccessException {
		boolean notNull = true;
		Class<?> type = field.getType();
		if (type == int.class) {
			notNull = ((Integer) field.get(qm) == 0 ? false : true);
		} else if (type == double.class) {
			notNull = ((Double) field.get(qm) == 0.0 ? false : true);
		} else if (type == float.class) {
			notNull = ((Float) field.get(qm) == 0.0 ? false : true);
		} else if (type == boolean.class) {
			notNull = ((Boolean) field.get(qm) == false ? false : true);
		} else if (type == char.class) {
			notNull = ((Character) field.get(qm) == '\u0000' ? false : true);
		} else if (type == byte.class) {
			notNull = ((Byte) field.get(qm) == 0 ? false : true);
		} else if (type == short.class) {
			notNull = ((Short) field.get(qm) == 0 ? false : true);
		}
		return notNull;
	}

	public static boolean notEqualsIn(String value, boolean ignoreCase, String... strs) {
		boolean isIn = false;
		for (int i = 0; i < strs.length; i++) {
			if (ignoreCase) {
				isIn = strs[i].equals(value);
			} else {
				isIn = strs[i].equalsIgnoreCase(value);
			}
		}
		return isIn;
	}

	/**
	 * 从一个字符串中找到某个字符串出现的位置，从指定开始的位置开始查找
	 * 
	 * @param str
	 * @return
	 */
	public static int indexOfIgnoreCase(String str, int fromIndex) {
		str = str.toLowerCase();
		return str.indexOf(str, fromIndex);

	}

	public static StringBuilder deleteLastChar(StringBuilder sb) {
		if (sb != null && sb.length() > 0) {
			return sb.deleteCharAt(sb.length() - 1);
		} else {
			return sb;
		}
	}

	public static String deleteLastCharToString(StringBuilder sb) {
		return deleteLastChar(sb).toString();
	}

	public static <T extends Collection<K>, K> List<K> toList(T cols) {
		return new ArrayList<K>(cols);
	}

	public static void setAccessable(String fieldName, Class<?> beanClass) {
		try {
			Field field = beanClass.getDeclaredField(fieldName);
			if (!field.isAccessible()) {
				field.setAccessible(true);
			}
		} catch (Exception e) {
			log.debug("can not set accessible for field : {} in class : {} ", fieldName, beanClass);
		}
	}

	private static final String formatRegex = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)   (([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";

	public static boolean isTimeStr(String timeFormat) {
		Pattern pattern = Pattern.compile(formatRegex);
		return pattern.matcher(timeFormat).matches();
	}

	public static String regDate(String date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if (formatter.format(formatter.parse(date)).equals(date)) {
				return formatter.format(formatter.parse(date));
			} else {
				return "";
			}
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 添加这个方法的原因:<br/>
	 * 因为之前都是调用 , Collections.emptyList(); ,<br/>
	 * 这个方法会返回一个EmptyList对象，该对象继承AbstractList父类 其add方法未实现.<br/>
	 * 所以在使用底层方法返回的Collections.emptyList() 对象再添加新数据的时候，会报
	 * java.lang.UnsupportedOperationException
	 * 
	 * @return
	 */
	public static <T> List<T> emptyList() {
		return new ArrayList<>();
	}

	/**
	 * @see GenericsUtils.emptyList()
	 * @return
	 */
	public static <K, V> Map<K, V> emptyMap() {
		return new HashMap<>();
	}

	/**
	 * @see GenericsUtils.emptyList()
	 * @return
	 */
	public static <T> Set<T> emptySet() {
		return new HashSet<>();
	}

	/**
	 * @param key
	 * @param listValue
	 */
	public static <K, V> void addListIfNotExists(Map<K, List<V>> map, K key, V listValue) {
		List<V> list = map.get(key);
		if (isNullOrEmpty(list)) {
			list = new ArrayList<>();
			map.put(key, list);
		}
		list.add(listValue);
	}

	private static final String COMMON_SPLIT_RETEX = "[,|:&#]+";

	public static <T> Set<T> stringToNumber(String numStr, Class<T> numType) {
		return stringToNumber(numStr, COMMON_SPLIT_RETEX, numType);
	}

	@SuppressWarnings("unchecked")
	public static <T> Set<T> stringToNumber(String numStr, String splitRegex, Class<T> numType) {
		if (GenericsUtils.isNullOrEmpty(numStr)) {
			return emptySet();
		}
		Set<T> retSet = null;
		try {
			String[] allValues = numStr.split(splitRegex);
			retSet = Sets.newHashSet();
			for (String value : allValues) {
				Object numValue = getNumValueFromStr(numType, value);
				retSet.add((T) numValue);
			}
		} catch (Exception e) {
			log.warn("can not parse str :{} cause by :{} ", numStr, e);
			retSet = new HashSet<>();
		}
		return retSet;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> stringToNumberList(String numStr, String splitRegex, Class<T> numType) {
		if (GenericsUtils.isNullOrEmpty(numStr)) {
			return emptyList();
		}

		String[] allValues = numStr.split(splitRegex);
		List<T> retList = new ArrayList<>();
		for (String value : allValues) {
			Object numValue = getNumValueFromStr(numType, value);
			retList.add((T) numValue);
		}
		return retList;
	}

	/**
	 * @param numType
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getNumValueFromStr(Class<T> numType, String value) {
		Object numValue = null;
		if (numType == Integer.class || numType == int.class) {
			numValue = Integer.parseInt(value);
		} else if (numType == Float.class || numType == float.class) {
			numValue = Float.parseFloat(value);
		} else if (numType == Short.class || numType == short.class) {
			numValue = Short.parseShort(value);
		} else if (numType == Long.class || numType == long.class) {
			numValue = Long.parseLong(value);
		} else if (numType == Double.class || numType == double.class) {
			numValue = Double.parseDouble(value);
		} else if (numType == Byte.class || numType == byte.class) {
			numValue = Byte.parseByte(value);
		} else if (numType == Character.class || numType == char.class) {
			numValue = new Character(value.charAt(0));
		}
		return (T) numValue;
	}

	/**
	 * 打印异常,logger 对象在error输出方法里面输出常规信息的时候,异常栈信息会缺失,需要分开打印
	 * 
	 * @param logger
	 *            :异常对象
	 * @param ex
	 *            :异常对象
	 * @param format
	 *            :输出格式,同 can not find any orgInfo with orgId:{} ,使用{}占位
	 * @param infos
	 *            :需要输出的占位信息
	 */
	public static void logErrorAndInfo(Logger logger, Exception ex, String format, Object... infos) {
		if (GenericsUtils.notNullAndEmpty(format)) {
			format = format.replace("{}", "%s");
			logger.info("[Pring Error Info ] {} ", String.format(format, infos));
		}
		logger.error("[exception] {} ", ex);// the really excepiton not in
											// GenericsUtils ,please find out
											// the
											// exception
											// position from StackTrace
	}

	public static void logErrorAndInfo(Class<?> clazz, Exception ex, String format, Object... infos) {
		Logger log = LoggerFactory.getLogger(clazz);
		logErrorAndInfo(log, ex, format, infos);
	}

	/**
	 * @return
	 */
	public static String formatOutput(String format, Object... params) {
		if (GenericsUtils.isNullOrEmpty(params) || GenericsUtils.isNullOrEmpty(format)) {
			return format;
		}
		if (format.contains("{}")) {
			format = format.replace("{}", "%s");
		}
		return String.format(format, params);
	}

	/**
	 * 对数据进行分组排序操作
	 * 
	 * @param list
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<Object, Object> groupSortForRetMap(Collection<Object> list, List<Comparator> comparators) {
		if (GenericsUtils.isNullOrEmpty(list) || GenericsUtils.isNullOrEmpty(comparators)) {
			throw new IllegalArgumentException("illegal arguments");
		}
		Map<Object, Object> retMap = Maps.newLinkedHashMap();
		Map<Object, List<Object>> tempMap = Maps.newHashMap();
		Comparator groupSortComparator = comparators.remove(0);
		Set<Object> keys = Sets.newHashSet();
		boolean isSortWithMulit = false;
		if (groupSortComparator instanceof MulitGroupSortComparator) {
			isSortWithMulit = true;
		}
		for (Object obj : list) {
			Object pid = null;
			if (groupSortComparator instanceof comparatorSuper) {
				pid = ((comparatorSuper) groupSortComparator).getPid(obj);
			}
			keys.add(pid);
			if (isSortWithMulit) {
				pid = ((((MulitGroupSortComparator) groupSortComparator).getPid(obj)).getPid());
			}
			addListIfNotExists(tempMap, pid, obj);
		}
		ArrayList<Object> keyReplace = Lists.newArrayList(keys);
		Collections.sort(keyReplace, groupSortComparator);
		for (Object key : keyReplace) {
			if (isSortWithMulit) {
				key = ((MulitGroupSorter) key).getPid();
			}
			List<Object> remove = tempMap.remove(key);
			// 默认相同pid的数据只对第一个取值
			if (GenericsUtils.isNullOrEmpty(remove)) {
				continue;
			}
			if (GenericsUtils.isNullOrEmpty(comparators)) {
				retMap.put(key, remove);
			} else {
				List<Comparator> comparatorList = Lists.newArrayList(comparators);
				retMap.put(key, groupSortForRetMap(remove, comparatorList));
			}
		}
		return retMap;
	}

	@SuppressWarnings({ "rawtypes" })
	public static <T> List<T> groupSort(Collection<Object> list, List<Comparator> comparators) {
		Map<Object, Object> groupSortForRetMap = groupSortForRetMap(list, comparators);
		System.out.println("groupSortForRetMap is : " + groupSortForRetMap);
		List<T> retList = findFromMap(groupSortForRetMap);
		return retList;
	}

	/**
	 * @param groupSortForRetMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <T> List<T> findFromMap(Map<Object, Object> groupSortForRetMap) {
		List<T> retList = Lists.newArrayList();
		for (Map.Entry<Object, Object> entry : groupSortForRetMap.entrySet()) {
			Object value = entry.getValue();
			if (value instanceof Map<?, ?>) {
				retList.addAll(GenericsUtils.<T>findFromMap((Map<Object, Object>) value));
			} else if (value instanceof Collection<?>) {
				for (Object val : (Collection<?>) value) {
					retList.add((T) val);
				}
			}
		}
		return retList;
	}

	public static interface comparatorSuper<F, T> {
		public F getPid(T obj);
	}

	public static interface GroupSortComparator<F, T> extends Comparator<F>, comparatorSuper<F, T> {

	}

	public static interface MulitGroupSortComparator<T>
			extends Comparator<MulitGroupSorter<T>>, comparatorSuper<MulitGroupSorter<T>, T> {

		public MulitGroupSorter<T> getPid(T obj);

	}

	@Data
	public static abstract class MulitGroupSorter<T> {

		private T obj;

		public abstract Object getPid();

		/**
		 * @param obj2
		 */
		public MulitGroupSorter(T obj2) {
			obj = obj2;
		}

	}

	/**
	 * 
	 */
	public static void testSpitAndGorup() {
		// testMethod();
		Collection<Object> users = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			User user = null;
			if (i % 2 == 0) {
				user = new User("Pary ", "paryAge" + i, (int) (Math.random() * 10));
			} else {
				user = new User("Rezar", "rezarAge" + i, (int) (Math.random() * 10));
			}
			users.add(user);
		}
		GroupSortComparator<String, User> comparators = new GroupSortComparator<String, User>() {

			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}

			@Override
			public String getPid(User obj) {
				return obj.getName();
			}
		};

		GroupSortComparator<Integer, User> comparators2 = new GroupSortComparator<Integer, User>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}

			@Override
			public Integer getPid(User obj) {
				return obj.getType();
			}
		};
		@SuppressWarnings({ "rawtypes" })
		List<Comparator> lists = Lists.newArrayList();
		lists.add(comparators);
		lists.add(comparators2);
		List<User> groupSort = GenericsUtils.groupSort(users, lists);
		for (User user : groupSort) {
			System.out.println("name:" + user.getName() + " -- type:" + user.getType());
		}
	}

	/**
	 * 
	 */
	public static void testMethod() {
		System.out.println(formatOutput("i am :{} and i love banlangen", "Rezar"));

		Collection<Long> userIds = Arrays.asList(445678L);
		System.out.println(GenericsUtils.isNullOrEmpty(userIds));
	}

	// 测试类
	@Data
	public static class User {
		private String name;
		private String age;
		private Integer type;

		/**
		 * @param name
		 * @param age
		 * @param type
		 */
		public User(String name, String age, Integer type) {
			super();
			this.name = name;
			this.age = age;
			this.type = type;
		}

		public static void logWithMark(Logger logger, String mark, String format, Object... infos) {
			logger.info("[{}] " + format, mark, infos);
		}
	}

	/**
	 * 将一个数组数据按照每个分组里面数据量最相近的方式划分为targetNum个分组
	 * 
	 * @param values
	 * @param targetNum
	 * @return
	 */
	public static <T> List<List<T>> divideToSomeCountArray(List<T> values, int targetNum) {

		return null;
	}

	/**
	 * 未完全均分,待上面的方法实现
	 * 
	 * @param lists
	 * @param limit
	 * @return
	 */
	public static <T> List<List<T>> splitList(List<T> lists, int limit) {
		int size = lists.size();
		List<List<T>> list = new ArrayList<List<T>>();
		if (limit > size) {
			list.add(lists);
			return list;
		}
		int result = 0;
		for (int i = 0; i < size; i = i + limit) {
			result = i + limit;
			if (result > size) {
				result = size;
			}
			list.add(lists.subList(i, result));
		}
		return list;
	}

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> findGenericsTypeFromSuperClass(Class<?> superClass) {
		ParameterizedType superType = (ParameterizedType) superClass.getGenericSuperclass();
		return (Class<T>) superType.getActualTypeArguments()[0];
	}

	/**
	 * @param endDate
	 * @param string
	 * @return
	 */
	public static Date formatDateStr(String dateStr, String formatStr) {
		if (GenericsUtils.isNullOrEmpty(dateStr)) {
			return null;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
			return sdf.parse(dateStr);
		} catch (Exception e) {
			log.warn("can not parserValueStr:{} and error is:{} ", dateStr, e);
			return new Date();
		}
	}

	public static final String simple_format = "yyyy-MM-dd HH:mm:ss.SSS";

	/**
	 * @param endDate
	 * @param string
	 * @return
	 */
	public static String simpleDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat(simple_format);
		return sdf.format(new Date());
	}

	/**
	 * @param fieldValueMap
	 * @return
	 */
	public static <K, V> Map<K, V> copyMap(Map<K, V> fieldValueMap) {
		if (GenericsUtils.isNullOrEmpty(fieldValueMap)) {
			return GenericsUtils.emptyMap();
		}
		Map<K, V> newRetMap = new HashMap<>(fieldValueMap.size());
		for (K key : fieldValueMap.keySet()) {
			newRetMap.put(key, fieldValueMap.get(key));
		}
		return newRetMap;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getNullWithoutError(Map<String, Object> options, String key) {
		if (GenericsUtils.isNullOrEmpty(options) || GenericsUtils.isNullOrEmpty(key)) {
			return null;
		}
		Object object = options.get(key);
		if (object == null) {
			return null;
		}
		try {
			return (T) object;
		} catch (Exception e) {
			log.info("error while cast object:{} with class:{} ", object, object.getClass().getName());
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> FillInfoContext<T> createInfoFillContext(List<T> sourceObjes,
			FillInfoCallback... fillInfoCallbackes) {
		if (GenericsUtils.notNullAndEmpty(fillInfoCallbackes)) {
			return new FillInfoContext<>(sourceObjes)
					.addInfoFiller(Arrays.<FillInfoCallback<T>>asList(fillInfoCallbackes));
		} else {
			return new FillInfoContext<>(sourceObjes);
		}
	}

	public static interface FillInfoCallback<T> {
		public void fillInfo(Collection<T> objes, FillInfoContext<T> context);
	}

	public static final class FillInfoContext<T> {

		private Collection<T> sourceObjs = null;
		private Map<String, Object> context = new HashMap<>();
		private List<FillInfoCallback<T>> infoFillers = new ArrayList<>();

		public FillInfoContext() {
		}

		/**
		 * @param fillInfoCallbackes
		 * @return
		 */
		public FillInfoContext<T> addInfoFiller(Collection<FillInfoCallback<T>> infoFilles) {
			this.infoFillers.addAll(infoFillers);
			return this;
		}

		public FillInfoContext(Collection<T> sourceObjes) {
			this.sourceObjs = sourceObjes;
		}

		public void addInfo(String infoKey, Object info) {
			context.put(infoKey, info);
		}

		public void addInfoFiller(String fieldKey, FillInfoCallback<T> infoFiller) {
			infoFillers.add(infoFiller);
		}

		public void addSourceObj(T sourceObj) {
			this.sourceObjs.add(sourceObj);
		}

		@SuppressWarnings("unchecked")
		public <E> E getInfoSource(String infoKey) {
			Object infoObj = context.get(infoKey);
			if (infoObj == null) {
				return null;
			}
			return (E) infoObj;
		}

		public void fillInfo() {
			if (this.sourceObjs != null && GenericsUtils.notNullAndEmpty(this.infoFillers)) {
				for (FillInfoCallback<T> infoFiller : this.infoFillers) {
					infoFiller.fillInfo(sourceObjs, this);
				}
			}
		}
	}

	public static interface InitInterface<P, V> {
		public V init(P params);
	}

	static class SafeOpMap<K, V> extends HashMap<K, V> {

		private InitInterface<K, V> initer;

		public SafeOpMap(InitInterface<K, V> initInterface) {
			this.initer = initInterface;
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@SuppressWarnings("unchecked")
		public V get(Object key) {
			if (!super.containsKey(key)) {
				synchronized (key.getClass().toString()) {
					if (!this.containsKey(key)) {
						try {
							this.put((K) key, initer.init((K) key));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			return super.get(key);
		}

	}

	public static <K, V> Map<K, V> createSafeMap(InitInterface<K, V> initInterface) {
		return new SafeOpMap<>(initInterface);
	}

	/**
	 * @param constructor
	 */
	public static <T> T instanceObj(Constructor<T> constructor) {
		try {
			if (!constructor.isAccessible()) {
				constructor.setAccessible(true);
			}
			return constructor.newInstance(new Object[] {});
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException("can not init obj with constructor :" + constructor.toGenericString());
		}
	}

	/**
	 * @return
	 */
	public static <T> Iterator<T> defaultIteartor() {
		return new Iterator<T>() {
			@Override
			public boolean hasNext() {
				return false;
			}

			@Override
			public T next() {
				return null;
			}
		};
	}

	public static class RetryTaskHelper implements Runnable {

		private volatile BlockingQueue<RetryTaskEntry> backlog = new LinkedBlockingQueue<>();
		private volatile BlockingQueue<RetryTaskEntry> active = new LinkedBlockingQueue<>();

		private Semaphore flag = new Semaphore(1);
		private volatile boolean isStart = false;
		private Thread thread;
		private AtomicInteger failureCount = new AtomicInteger(0);

		public void addRetryTask(int targetRetryTimes, Runnable runTask) {
			RetryTaskEntry retryTaskEntry = new RetryTaskEntry(targetRetryTimes, runTask);
			this.backlog.add(retryTaskEntry);
			if (!this.isStart) {
				boolean tryAcquire = this.flag.tryAcquire();
				if (tryAcquire) {
					this.start();
				}
			}
		}

		/**
		 * 
		 */
		private void start() {
			this.thread = new Thread(this);
			this.thread.start();
			this.isStart = true;
		}

		@Override
		public void run() {
			while (true) {
				RetryTaskEntry entry = this.active.poll();
				/**
				 * 如果当前active队列里面没有消息,则将当前的任务队列切到backlog队列进行处理
				 */
				if (entry == null) {
					try {
						entry = backlog.take();
					} catch (InterruptedException e) {
						throw new IllegalStateException();
					}
					BlockingQueue<RetryTaskEntry> tempQueue = backlog;
					backlog = active;
					active = tempQueue;
				}
				if (!entry.canRetry()) {
					continue;
				}
				try {
					entry.run();
				} catch (Exception e) {
					int incrementAndGet = failureCount.incrementAndGet();
					log.info("failureCount is:{} and taskName : {} ", incrementAndGet, entry.getTaskName());
				}
			}
		}
	}

	public static class RetryTaskEntry {

		private final int targetRetryTimes;
		private int currentRetryTimes;
		private Runnable runTask;
		private String taskName = "";

		public RetryTaskEntry(int targetRetryTimes, Runnable runTask) {
			this(targetRetryTimes, runTask, "default");
		}

		public RetryTaskEntry(int targetRetryTimes, Runnable runTask, String taskName) {
			this.targetRetryTimes = targetRetryTimes;
			this.runTask = runTask;
			this.taskName = taskName;
		}

		public String getTaskName() {
			return this.taskName;
		}

		public boolean canRetry() {
			return this.currentRetryTimes > this.targetRetryTimes;
		}

		public void run() {
			this.currentRetryTimes++;
			this.runTask.run();
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] createArray(Class<T> compentType, int size) {
		return (T[]) Array.newInstance(compentType, size);
	}

}
