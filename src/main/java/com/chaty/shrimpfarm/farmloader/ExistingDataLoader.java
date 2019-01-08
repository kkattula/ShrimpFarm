package com.chaty.shrimpfarm.farmloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chaty.shrimpfarm.model.Activity;
import com.chaty.shrimpfarm.model.Feed;
import com.chaty.shrimpfarm.model.FeedAct;
import com.chaty.shrimpfarm.model.PondActivity;
import com.chaty.shrimpfarm.repository.ActivityRepo;

@Component
public class ExistingDataLoader {

	@Autowired
	ActivityRepo activityRepo;


	public void loadExisting() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");

		String fileLocation = "/Users/chaty/Documents/Aerator/ExcelData/FeedP2.xlsx";

		try {

			FileInputStream excelFile = new FileInputStream(new File(fileLocation));
			@SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook(excelFile);

			Activity activity = new Activity();
			activity.setSeason("Winter-2018");

			PondActivity pondAct = new PondActivity();
			pondAct.setPondUUID("P2");

			List<FeedAct> feedActList = new ArrayList<>();

			List<String> meals = new ArrayList<>();
			boolean firstRow = true;

			Sheet datatypeSheet = workbook.getSheetAt(1);
			Iterator<Row> iterator = datatypeSheet.iterator();

			while (iterator.hasNext()) {

				Row currentRow = iterator.next();

				if (firstRow) {

					meals.add(currentRow.getCell(1).toString());
					meals.add(currentRow.getCell(2).toString());
					meals.add(currentRow.getCell(3).toString());
					meals.add(currentRow.getCell(4).toString());

				} else {

					if (currentRow.getCell(0).toString().matches("[0-9]{1,2}-[a-zA-Z]{3}-[0-9]{4}")) {
						FeedAct feedAct = new FeedAct();
						feedAct.setDate(LocalDate.parse(currentRow.getCell(0).toString(), formatter));

						List<Feed> feedList = new ArrayList<>();

						for (int j = 0; j < meals.size(); j++) {
							Feed feed = new Feed();
							feed.setTime(meals.get(j));
							feed.setAmount(Float.parseFloat(currentRow.getCell(j + 1).toString()));
							feedList.add(feed);
						}

						feedAct.setFeedList(feedList);
						feedActList.add(feedAct);
					}

				}

				firstRow = false;

			}

			pondAct.setFeedList(feedActList);
			activity.setPondActivityList(Arrays.asList(pondAct));
	
			activityRepo.save(activity);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
