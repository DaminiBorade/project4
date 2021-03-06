package in.co.rays.project4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project4.bean.BaseBean;
import in.co.rays.project4.bean.MarksheetBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.model.MarksheetModel;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.PropertyReader;
import in.co.rays.project4.util.ServletUtility;



/**
 * Marksheet List functionality Controller. Performs operation for list, search
 * and delete operations of Marksheet.
 * @author Damini
 *
 */
@WebServlet(name="MarksheetListCtl",urlPatterns={"/ctl/MarksheetListCtl"})
public class MarksheetListCtl extends BaseCtl {
  //  public static Logger log = Logger.getLogger(MarksheetListCtl.class);
	
	protected void preload(HttpServletRequest request){
		MarksheetModel model = new MarksheetModel();
		try{
			List clist =model.list();
			request.setAttribute("rollNo", clist);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
    	System.out.println("in populated");
        MarksheetBean bean = new MarksheetBean();
//bean.setId(DataUtility.getInt(request.getParameter("rollno")));
//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>"+request.getParameter("rollno"));
        bean.setId(DataUtility.getInt(request.getParameter("rollNo")));
System.out.println("Rollno>>>>>>>>>>>>>>>>>>>>"+request.getParameter("rollNo"));
        bean.setName(DataUtility.getString(request.getParameter("name")));
        

        return bean;
    }

    /**
     * ContainsDisplaylogics
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;

        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
                .getValue("page.size")) : pageSize;

        MarksheetBean bean = (MarksheetBean) populateBean(request);

        List list = null;
        MarksheetModel model = new MarksheetModel();
        try {
            list = model.search(bean, pageNo, pageSize);
        } catch (ApplicationException e) {
          //  log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        }

        if (list == null || list.size() == 0) {
            ServletUtility.setErrorMessage("No record found ", request);
        }
        ServletUtility.setList(list, request);
        ServletUtility.setPageNo(pageNo, request);
        ServletUtility.setPageSize(pageSize, request);
        ServletUtility.forward(getView(), request, response);
       // log.debug("MarksheetListCtl doGet End");

    }

    /**
     * Contains Submit logics
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

    //	log.debug("MarksheetListCtl doPost Start");

        List list = null;
        
    	List nextList=null;
        String op = DataUtility.getString(request.getParameter("operation"));

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;

        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

        MarksheetBean bean = (MarksheetBean) populateBean(request);


        // get the selected checkbox ids array for delete list
        String[] ids = request.getParameterValues("ids");

        MarksheetModel model = new MarksheetModel();


                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }

             else if (OP_NEW.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.MARKSHEET_CTL, request,
                        response);
                return;
            }

             else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request,
                        response);
                return;
                
             }else if (OP_DELETE.equalsIgnoreCase(op)) {
                pageNo = 1;
                if (ids != null && ids.length > 0) {
                    MarksheetBean deletebean = new MarksheetBean();
                    for (String id : ids) {
                        deletebean.setId(DataUtility.getInt(id));
                        try {
							model.delete(deletebean);
						} catch (ApplicationException e) {
							e.printStackTrace();
							ServletUtility.handleException(e, request, response);
							return;
						}
                        ServletUtility.setSuccessMessage(" Marksheet Data Successfully Deleted ", request);
                    }
                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }
            }
            try {
				list = model.search(bean, pageNo, pageSize);
				
				 nextList=model.search(bean,pageNo+1,pageSize);
					
					request.setAttribute("nextlist", nextList.size());
					
				ServletUtility.setBean(bean, request);
            }
            catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
            ServletUtility.setList(list, request);
            if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
                ServletUtility.setErrorMessage("No record found ", request);
            }
            ServletUtility.setList(list, request);
            ServletUtility.setBean(bean, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);
            
           // log.debug("Marksheet List Ctl do post End");
        }

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.MARKSHEET_LIST_VIEW;
	}

}
