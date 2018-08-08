package com.qa.utils;

import java.util.List;

public class ListDataManager {

	public List<String> doCompareLists(List<String> selectingOffer, List<String> selectedOffers, int iteration)
	{
		if(selectingOffer.size()==selectedOffers.size() && selectedOffers.size()>0 && iteration<selectingOffer.size())
		{
			String valueFromSelectingOffer = selectingOffer.get(iteration).toString();
			if(selectedOffers.contains(valueFromSelectingOffer))
			{
				selectingOffer.remove(valueFromSelectingOffer);
				selectedOffers.remove(valueFromSelectingOffer);
				return doCompareLists(selectingOffer, selectedOffers, iteration);
			}
			else
			{
				if(selectingOffer.size()==0 && selectedOffers.size()==0)
				{
					return selectingOffer;
				}
				else
				{
					iteration++;
					return doCompareLists(selectingOffer, selectedOffers, iteration);
				}
			}
		}
		else
		{
			return selectingOffer;
		}
	}
}
