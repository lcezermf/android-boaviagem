package io.github.lccezinha.mytravel.provider;

import android.net.Uri;

public final class MyTravelContract {
	public static final String AUTHORITY = "io.github.lccezinha.mytravel.provider";
	public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);
	public static final String TRAVEL_PATH = "travel";
	public static final String SPENT_PATH = "spent";

	public static final class Travel{
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" +
						"vnd.io.github.lccezinha.mytravel.provider/travel";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" +
						"vnd.io.github.lccezinha.mytravel.provider/travel";

		public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, TRAVEL_PATH);
		public static final String _ID = "_ID";
		public static final String DESTINY = "DESTINY";
		public static final String DATE_START = "DATE_START";
		public static final String DATE_FINISH = "DATE_FINISH";
		public static final String BUDGET = "BUDGET";
		public static final String PEOPLE_QUANTITY = "PEOPLE_QUANTITY";
	}
	
	public static final class Spent{
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" +
				"vnd.io.github.lccezinha.mytravel.provider/spent";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" +
				"vnd.io.github.lccezinha.mytravel.provider/spent";

		public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, SPENT_PATH);
		public static final String _ID = "_ID";
		public static String TRAVEL_ID = "TRAVEL_ID";
		public static final String CATEGORY = "CATEGORY";
		public static final String DATE = "DATE";
		public static final String DESCRIPTION = "DESCRIPTION";
		public static final String PLACE = "PLACE";
	}

}
