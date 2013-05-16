package org.onlinetaskforce.web.frontend.application;

import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.onlinetaskforce.business.services.GebruikerService;
import org.onlinetaskforce.persistence.utils.SystemConstants;
import org.onlinetaskforce.web.frontend.authorisation.AuthorisationStrategy;
import org.onlinetaskforce.web.frontend.fileupload.FileManageResourceReference;
import org.onlinetaskforce.web.frontend.fileupload.FileUploadResourceReference;
import org.onlinetaskforce.web.frontend.listeners.BlockUiListener;
import org.onlinetaskforce.web.frontend.pages.HomeLoginPage;
import org.onlinetaskforce.web.frontend.pages.HomePage;
import org.onlinetaskforce.web.frontend.pages.gebruikersbeheer.DetailUserPage;
import org.onlinetaskforce.web.frontend.pages.gebruikersbeheer.OverviewUserPage;
import org.onlinetaskforce.web.frontend.pages.gebruikersbeheer.PictureUserPage;
import org.onlinetaskforce.web.frontend.pages.gebruikersbeheer.WachtwoordPage;
import org.onlinetaskforce.web.frontend.pages.reservatiebeheer.DetailReservatiePage;
import org.onlinetaskforce.web.frontend.pages.reservatiebeheer.OntvangstWagenPage;
import org.onlinetaskforce.web.frontend.pages.reservatiebeheer.OverviewReservatiePage;
import org.onlinetaskforce.web.frontend.pages.reservatiebeheer.ReservatieWizardPage;
import org.onlinetaskforce.web.frontend.pages.wagenbeheer.DetailWagenPage;
import org.onlinetaskforce.web.frontend.pages.wagenbeheer.OverviewWagenPage;
import org.onlinetaskforce.web.frontend.session.OtfWebSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Locale;


/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 */
@Component("wicketApplication")
public class OtfApplication extends WebApplication {
    private FileUploadResourceReference fileUpload;
    private FileManageResourceReference fileDownload;

    @Override
    public Session newSession(Request request, Response response) {
        return new OtfWebSession(request);
    };

    private String applicationVersion;
    /**
     * BusinessException prefix used in resource bundles
     */
    public static final String BUSINESSEXCEPTION_PREFIX = "BusinessException";
    public static final String BASE_FOLDER = System.getProperty("java.io.tmpdir");

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return HomeLoginPage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();
        // enforce dutch locale for Application
        Locale.setDefault(new Locale("nl"));
        //Session.get().setLocale(new Locale("nl"));

        // enable ajax debug etc.
		getDebugSettings().setDevelopmentUtilitiesEnabled(true);

        getSecuritySettings().setAuthorizationStrategy(new AuthorisationStrategy());

        getComponentInstantiationListeners().add(new SpringComponentInjector(this));

        getAjaxRequestTargetListeners().add(new AjaxRequestTarget.AbstractListener() {
            @Override
            public void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                super.updateAjaxAttributes(attributes);
                attributes.getAjaxCallListeners().add(new BlockUiListener());
            }
        });

		// make markup friendly as in deployment-mode
		getMarkupSettings().setStripWicketTags(true);

        mountPage("/home-login/", getHomePage());
        mountPage("/home/", HomePage.class);
        mountPage("/home/gebruikersbeheer/add-photo", PictureUserPage.class);
        mountPage("/home/gebruikersbeheer/overview", OverviewUserPage.class);
        mountPage("/home/gebruikersbeheer/detail", DetailUserPage.class);
        mountPage("/home/gebruikersbeheer/wijzig-wachtwoord", WachtwoordPage.class);
        mountPage("/home/reservatiebeheer/overview", OverviewReservatiePage.class);
        mountPage("/home/reservatiebeheer/detail", DetailReservatiePage.class);
        mountPage("/home/reservatiebeheer/wizard", ReservatieWizardPage.class);
        mountPage("/home/reservatiebeheer/ontvang", OntvangstWagenPage.class);
        mountPage("/home/wagenbeheerbeheer/overview", OverviewWagenPage.class);
        mountPage("/home/wagenbeheerbeheer/detail", DetailWagenPage.class);

        fileUpload = new FileUploadResourceReference(BASE_FOLDER);
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring/applicationcontext.xml");
        fileUpload.setGebruikerService(context.getBean(GebruikerService.class));
        fileDownload = new FileManageResourceReference(BASE_FOLDER);

        mountResource("/home/fileManager", fileDownload);
		mountResource("/home/fileUpload", fileUpload);

	}

    @Override
    public RuntimeConfigurationType getConfigurationType() {
        return SystemConstants.DEVELOPMENT ? RuntimeConfigurationType.DEVELOPMENT : RuntimeConfigurationType.DEPLOYMENT;
    }

    @Override
    protected IConverterLocator newConverterLocator() {
        final ConverterLocator converterLocator = (ConverterLocator) super.newConverterLocator();
        converterLocator.set(Date.class, new PatternDateConverter("dd/MM/yyyy", false));
        converterLocator.set(java.util.Date.class, new PatternDateConverter("dd/MM/yyyy", false));
        converterLocator.set(Timestamp.class, new PatternDateConverter("dd/MM/yyyy HH:mm", false));
        return converterLocator;
    }


    public String getApplicationVersion() {
        return applicationVersion;
    }
}
