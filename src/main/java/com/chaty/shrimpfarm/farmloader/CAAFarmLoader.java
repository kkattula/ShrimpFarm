package com.chaty.shrimpfarm.farmloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chaty.shrimpfarm.repository.CAAFarmRepo;
import com.chaty.shrimpfarm.repository.StateRepo;

@Component
public class CAAFarmLoader {

	@Autowired
	StateRepo stateRepo;

	@Autowired
	CAAFarmRepo caaRepo;

	public void loadCAAFarms() {

		String fileLocation = "/Users/chaty/Documents/Aerator/FarmersList.xlsx";

		try {

			FileInputStream excelFile = new FileInputStream(new File(fileLocation));
			@SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook(excelFile);

			State state = new State();
			state.setName("Andhra Pradesh");
			List<District> distList = new ArrayList<>();

			for (int i = 1; i < workbook.getNumberOfSheets(); i++) {

				Sheet datatypeSheet = workbook.getSheetAt(i);
				Iterator<Row> iterator = datatypeSheet.iterator();

				District district = new District();

				Set<String> mandalList = new HashSet<>();

				district.setName(datatypeSheet.getSheetName());

				List<CAAFarm> farmList = new ArrayList<>();

				while (iterator.hasNext()) {

					Row currentRow = iterator.next();

					CAAFarm farm = new CAAFarm();
					farm.setRegNo(currentRow.getCell(1).toString());
					farm.setName(currentRow.getCell(2).toString());
					farm.setFathersName(currentRow.getCell(3).toString());
					farm.setAddress(currentRow.getCell(4).toString());
					farm.setMandal(currentRow.getCell(5).toString());
					mandalList.add(farm.getMandal());
					farm.setRevenueVillage(currentRow.getCell(6).toString());
					farm.setWaterSpread(currentRow.getCell(7).toString());
					farm.setSurveyList(Arrays.asList(currentRow.getCell(8).toString().split(",")));
					farm.setIssueDate(currentRow.getCell(9).toString());
					farm.setStatus(currentRow.getCell(10).toString());

					farmList.add(farm);

				}

				Set<Mandal> mandalModelList = mandalList.stream().map(obj -> {
					Mandal mandal = new Mandal();
					mandal.setName(obj);
					mandal.setUuid(UUID.randomUUID().toString());
					return mandal;
				}).collect(Collectors.toSet());

				List<CAAFarm> modList = farmList.stream().map(obj -> {
					CAAFarm modFarm = obj;
					String uuid = mandalModelList.stream().filter(m -> m.getName().equals(obj.getMandal())).findFirst()
							.get().getUuid();
					modFarm.setUuid(uuid);
					return modFarm;
				}).collect(Collectors.toList());

				district.setMandals(mandalModelList);
				distList.add(district);
				
				
				caaRepo.save(modList);

			}
			
			state.setDistricts(distList);
			
			stateRepo.save(state);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
