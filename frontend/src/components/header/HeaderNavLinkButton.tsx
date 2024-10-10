import { Link } from 'react-router-dom';

type HeaderNavLinkButtonProps = {
  link: string;
  text: string;
};

export default function HeaderNavLinkButton({ link, text }: HeaderNavLinkButtonProps) {
  return (
    <li className="nav-item">
      <Link className="nav-link" to={link}>
        {text}
      </Link>
    </li>
  );
}
