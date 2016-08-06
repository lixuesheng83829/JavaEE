package cn.itcast.oa.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.itcast.oa.domain.Channel;

public class ChannelUtils {
	/**
	 * 遍历栏目树,得到所有的栏目列表,并修改名称以显示关联层次
	 * @param topList
	 * @return
	 */
	public static List<Channel> getAll(List<Channel> topList) {
		List<Channel> list = new ArrayList<Channel>();
		walkTrees(topList,"┣",list);
		return list;
	}
	/**
	 * 遍历部门树,把遍历出来的部门都放到指定的集合中,采用递归算法
	 * @param topList
	 * @param list
	 */
	private static void walkTrees(Collection<Channel> topList,String prefix, List<Channel> list){
		for(Channel top : topList){
			Channel copy = new Channel();
			copy.setId(top.getId());
			copy.setName(prefix + top.getName());
			list.add(copy);
			walkTrees(top.getChildren(),"----" + prefix,list);
		}
	}
}
