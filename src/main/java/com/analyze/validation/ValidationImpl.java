package com.analyze.validation;

import java.util.ArrayList;

import com.analyze.repositories.ValidationRepo;

public class ValidationImpl implements ValidationRepo {

	@Override
	public int checkUniqueAd(ArrayList<String> list) {
		int br = 0;
		
		for (int j = 0; j < list.size(); j++) {
			for (int i = j+1; i < list.size(); i++) {
				if (list.get(j).equals(list.get(i))) {
					System.out.println("Isti oglas");
					br++;
					System.out.println(list.get(j));
				}
			}
		}

		return br;

	}

}
