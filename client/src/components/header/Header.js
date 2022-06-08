import { useLocation, Link } from 'react-router-dom';
import styles from './Header.module.css';

function Header() {
    const location = useLocation();

    function generateLinks() {
        let pathname = location.pathname;
        if (pathname.search('^/get-patient/.*$') !== -1 || 
            pathname.search('^/add-patient$') !== -1 ||
            pathname.search('^/gen-report/.*/.*$') !== -1)
            return  <Link className={'navbar-brand ' + styles.navbar_brand} to='/'>Пациенты</Link>
    };

    return (
        <header className={styles.content}>
            <div className={'container ' + styles.content_container}>
                <nav className={'navbar ' + styles.content_navbar}>
                    { generateLinks() }
                </nav>
            </div>
        </header>
    );
}

export default Header;