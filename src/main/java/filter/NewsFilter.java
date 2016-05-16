package filter;

import bl.ShowStockNews;
import database.GetStockNews;
import vo.StockNewsVO;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by song on 16-5-14.
 *
 * 负责加载新闻
 */
@WebFilter(filterName = "NewsFilter")
public class NewsFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;


        ShowStockNews stockNews = new ShowStockNews();
        List<StockNewsVO> news = stockNews.showStockNews("sh600015");

        request.setAttribute("news", news);

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }
}