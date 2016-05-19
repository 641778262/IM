package com.chen.imkit.contact.adapter;

import android.text.TextUtils;

import com.chen.imkit.NimUIKit;
import com.chen.imkit.cache.TeamDataCache;
import com.netease.nimlib.sdk.msg.attachment.NotificationAttachment;

public class TeamRecentViewHolder extends CommonRecentViewHolder {

	@Override
	protected String getContent() {
		String content = descOfMsg();

		String fromId = recent.getFromAccount();
		if (!TextUtils.isEmpty(fromId)
				&& !fromId.equals(NimUIKit.getAccount())
				&& !(recent.getAttachment() instanceof NotificationAttachment)) {
			String tid = recent.getContactId();
			String teamNick = getTeamUserDisplayName(tid, fromId);
			content = teamNick + ": " + content;
		}

		return content;
	}

	private String getTeamUserDisplayName(String tid, String account) {
		return TeamDataCache.getInstance().getTeamMemberDisplayName(tid, account);
	}

}
