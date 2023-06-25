package org.example.live.user.contants;

public enum UserTagsEnum {

	IS_RICH((long) Math.pow(2, 0), "rich user", "tag_info_01"),
    IS_VIP((long) Math.pow(2, 1), "VIP user", "tag_info_01"),
    IS_OLD_USER((long) Math.pow(2, 2), "old user", "tag_info_01");
	/**
	 * 000000
	 * rich:     000001 (2^0 = 1)
	 * vip:      000010 (2^1 = 2)
	 * old user: 000100 (2^2 = 4)
	 */
	
	long tag;
    String desc;
    String fieldName;
	
    UserTagsEnum(long tag, String desc, String fieldName) {
        this.tag = tag;
        this.desc = desc;
        this.fieldName = fieldName;
    }
    
    public long getTag() {
        return tag;
    }

    public String getDesc() {
        return desc;
    }

    public String getFieldName() {
        return fieldName;
    }
}
