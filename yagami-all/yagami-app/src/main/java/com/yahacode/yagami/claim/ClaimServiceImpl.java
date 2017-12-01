package com.yahacode.yagami.claim;

import com.yahacode.yagami.base.BaseDao;
import com.yahacode.yagami.base.BizfwServiceException;
import com.yahacode.yagami.base.common.StringUtils;
import com.yahacode.yagami.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zengyongli
 */
@Service
public class ClaimServiceImpl extends BaseServiceImpl<Claim> implements ClaimService {

    private ClaimDao claimDao;

    @Override
    public Claim initClaim(Claim claim) throws BizfwServiceException {
        claim.setDocumentGroupNo(StringUtils.generateUUID());
        claim.setStatus(Claim.STATUS_INIT);
        save(claim);
        return claim;
    }

    @Override
    public List<Claim> getClaimReviewList() throws BizfwServiceException {
        return queryByFieldAndValue(Claim.COLUMN_STATUS, Claim.STATUS_INIT);
    }

    @Override
    public List<Claim> getClaimReportList() throws BizfwServiceException {
        return claimDao.queryByFieldAndValues(Claim.COLUMN_STATUS, Claim.STATUS_INIT, Claim.STATUS_ACCEPT);
    }

    @Override
    public void acceptClaim(Claim claim) throws BizfwServiceException {
        claim.setStatus(Claim.STATUS_ACCEPT);
        claim.setAuditor(claim.getUpdateBy());
        update(claim);
    }

    @Override
    public void rejectClaim(Claim claim) throws BizfwServiceException {
        claim.setStatus(Claim.STATUS_REJECT);
        claim.setAuditor(claim.getUpdateBy());
        update(claim);
    }

    @Override
    public BaseDao<Claim> getBaseDao() {
        return claimDao;
    }

    @Autowired
    public void setClaimDao(ClaimDao claimDao) {
        this.claimDao = claimDao;
    }

}
