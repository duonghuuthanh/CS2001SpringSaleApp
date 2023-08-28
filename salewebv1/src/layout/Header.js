import { useContext, useEffect, useState } from "react";
import { Badge, Button, Col, Container, Form, Nav, Navbar, NavDropdown, Row } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import { MyCartContext, MyUserContext } from "../App";
import Apis, { endpoints } from "../configs/Apis";
import MySpinner from "./MySpinner";

const Header = () => {
    const [user, dispatch] = useContext(MyUserContext);
    const [cartCounter, ] = useContext(MyCartContext);
    const [categories, setCategories] = useState(null);
    const [kw, setKw] = useState("");
    const nav = useNavigate();

    const loadCates = async () => {
        let res = await Apis.get(endpoints['categories'])
        setCategories(res.data);
    }

    useEffect(() => {
        loadCates();
    }, [])

    const search = (evt) => {
        evt.preventDefault();
        nav(`/?kw=${kw}`)
    }

    const logout = () => {
        dispatch({
            "type": "logout"
        })
    }

    if (categories === null)
        return <MySpinner />;

    return (
    <>
       
        <Navbar expand="lg" className="bg-body-tertiary">
        <Container>
            <Navbar.Brand href="#home">&#128178; E-Commerce Website</Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="me-auto">
                <Link className="nav-link" to="/">&#127968; Trang chủ</Link>
                    
                    <NavDropdown title="Danh mục" id="basic-nav-dropdown">
                        {categories.map(c => {
                            let h = `/?cateId=${c.id}`;
                            return <Link className="dropdown-item" to={h}  key={c.id}>{c.name}</Link>
                        })}    
                        
                    </NavDropdown>
                    {user === null ? <>
                        <Link className="nav-link text-danger" to="/login">Đăng nhập</Link>
                        <Link className="nav-link text-danger" to="/register">Đăng ký</Link>
                        
                       
                    </>: <>
                        <Link className="nav-link text-danger" to="/">Chào {user.username}!</Link>
                        <Button variant="secondary" onClick={logout}>Đăng xuất</Button>
                    </>}
                    <Link className="nav-link text-danger" to="/cart">&#128722; <Badge bg="danger">{cartCounter}</Badge></Link>
                </Nav>
            </Navbar.Collapse>
            <Form onSubmit={search} inline>
                <Row>
                <Col xs="auto">
                    <Form.Control
                    type="text"
                    value={kw}
                    onChange={e => setKw(e.target.value)}
                    placeholder="Nhập từ khóa..." name="kw"
                    className=" mr-sm-2"
                    />
                </Col>
                <Col xs="auto">
                    <Button type="submit">Tìm</Button>
                </Col>
                </Row>
            </Form>
        </Container>
        
        </Navbar>  
    </>
    )
}

export default Header;