import styles from './Footer.module.css';
import symbol from '../../assets/symbol.svg';

function Footer() {
    return (
        <footer className={styles.content}>
            <div className={'container ' + styles.content_container}>
                <div className={'d-flex align-items-center gap-2 ' + styles.content_items}>
                    <img src={symbol} width='30' height='30' alt='symbol' />
                    <div className={styles.items_text}>Все права защищены @Alexey Bylinovitch 2022</div>
                </div>
            </div>
        </footer>
    );
}

export default Footer;