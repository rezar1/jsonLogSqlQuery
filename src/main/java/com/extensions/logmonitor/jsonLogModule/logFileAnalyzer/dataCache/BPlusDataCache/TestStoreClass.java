package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache;

import java.nio.ByteBuffer;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月20日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class TestStoreClass {

	public static void main(String[] args) {
		String dataFile = "/Users/rezar/Desktop/testTree";
		SeriAndDeser<UnicodeKey> unicodeKeySd = new SeriAndDeser<UnicodeKey>() {
			@Override
			public void serialize(UnicodeKey obj, ByteBuffer buff) {
				obj.serialize(buff);
			}

			@Override
			public UnicodeKey deserialize(ByteBuffer buf) {
				return UnicodeKey.deserialize(buf);
			}
		};
		SeriAndDeser<UserData> userDataSd = new SeriAndDeser<TestStoreClass.UserData>() {
			@Override
			public void serialize(UserData obj, ByteBuffer buff) {
				obj.serialize(buff);
			}

			@Override
			public UserData deserialize(ByteBuffer buf) {
				return UserData.deserialize(buf);
			}
		};

		BplusTree<UnicodeKey, UserData> tree = new BplusTree<>(20, dataFile, unicodeKeySd, userDataSd);
		byte[] orderType = new byte[] { 1, -1 };
		long time1, time2;
		time1 = System.currentTimeMillis();
		for (int i = 0; i < 1002030; i++) {
			UnicodeKey key = new UnicodeKey(2, orderType);
			key.addKeyObjValue(i);
			key.addKeyObjValue("name" + i);
			UserData userData2 = new UserData("name" + i, i);
			tree.insertOrUpdate(key, userData2);
			if (i == 22) {
				UserData userData = tree.get(key);
				System.out.println("key:" + userData);
			}
		}
		time2 = System.currentTimeMillis();
		System.out.println("create use time:" + (time2 - time1));
		UnicodeKey key = new UnicodeKey(2, orderType);
		key.addKeyObjValue(10);
		key.addKeyObjValue("name10");
		UserData userData = tree.get(key);
		time2 = System.currentTimeMillis();
		System.out.println(userData + "\tuserTime:" + (time2 - time1));
	}

	public static class UserData implements DataSizeCountable {

		private String name;
		private int age;

		/**
		 * @param string
		 * @param i
		 */
		public UserData(String name, int age) {
			this.name = name;
			this.age = age;
		}

		/**
		 * 
		 */
		public UserData() {
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the age
		 */
		public int getAge() {
			return age;
		}

		/**
		 * @param age
		 *            the age to set
		 */
		public void setAge(int age) {
			this.age = age;
		}

		@Override
		public String toString() {
			return "UserData [name=" + name + ", age=" + age + "]";
		}

		public void serialize(ByteBuffer buf) {
			buf.putInt(this.name.length());
			buf.put(this.name.getBytes());
			buf.putInt(this.age);
		}

		public static UserData deserialize(ByteBuffer buf) {
			UserData userData = new UserData();
			byte[] dst = new byte[buf.getInt()];
			buf.get(dst);
			userData.name = new String(dst);
			userData.age = buf.getInt();
			return userData;
		}

		@Override
		public int sizeOfData() {
			int size = 0;
			size += 4;
			size += this.name.length();
			size += this.name.getBytes().length;
			return size;
		}

	}

}
