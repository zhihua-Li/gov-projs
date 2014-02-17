/**
 * CodisBatchImportService.java
 *
 * 2014-1-20
 */
package com.lims.service.codisbatchimport;

import java.util.List;
import java.util.Map;

/**
 * @author lizhihua
 *
 */
public interface CodisBatchImportService {

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public List<List<Map<String, Object>>> insertBatchImportCodisSample(String consignOrganizationId, String reagentKitId, List<List<Map<String, Object>>> allCodisFolderInfoList, String sysUserId) throws Exception;

}
