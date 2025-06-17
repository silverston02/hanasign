
import React, { useEffect, useState } from "react";
import axios from "axios";

// Ant Design CDN 사용 (index.html에 추가 필요)
// <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/antd/dist/antd.min.css" />
import { Tree, Table, Button, Input, message } from "antd";

function App() {
    // 상태
    const [user, setUser] = useState(null); // 로그인 사용자 정보
    const [teams, setTeams] = useState([]); // 트리용 팀 데이터
    const [treeData, setTreeData] = useState([]); // 트리 변환 데이터
    const [selectedTeamId, setSelectedTeamId] = useState(null);
    const [members, setMembers] = useState([]);
    const [search, setSearch] = useState("");

    // 1. 로그인 사용자 정보 가져오기
    useEffect(() => {
        axios.get("/api/users/me")
            .then(res => setUser(res.data.data))
            .catch(() => message.error("로그인 정보를 불러올 수 없습니다."));
    }, []);

    // 2. 회사별 팀 트리 가져오기
    useEffect(() => {
        if (!user) return;
        axios.get(`/api/team/search?keyword=`, { /* 회사ID는 백엔드에서 user.email로 필터됨 */ })
            .then(res => {
                setTeams(res.data.data);
                setTreeData(makeTree(res.data.data));
            });
    }, [user]);

    // 3. 팀 선택 시 팀원 목록 가져오기
    useEffect(() => {
        if (!selectedTeamId) return;
        // 팀별 유저 API가 없으므로, 회사 전체 유저를 받아서 팀ID로 필터링한다고 가정
        axios.get(`/api/company/get/${user.companyId}`)
            .then(res => {
                // 회사에 속한 모든 유저를 받아온다고 가정 (실제 API에 맞게 수정)
                const allUsers = res.data.data.users || [];
                setMembers(allUsers.filter(u => u.teamId === selectedTeamId));
            });
    }, [selectedTeamId, user]);

    // 4. 검색
    const handleSearch = (value) => {
        setSearch(value);
        // 팀 검색
        axios.get(`/api/team/search?keyword=${value}`)
            .then(res => setTreeData(makeTree(res.data.data)));
        // 유저 검색 (회사 전체에서)
        // axios.get(`/api/users/search?keyword=${value}`) 등으로 확장 가능
    };

    // 트리 변환 함수 (flat -> nested)
    function makeTree(teams) {
        // 예시: [{id, teamName, parentId, ...}]
        const idMap = {};
        teams.forEach(t => idMap[t.id] = { ...t, key: t.id, title: t.teamName, children: [] });
        const tree = [];
        teams.forEach(t => {
            if (t.parentId && idMap[t.parentId]) {
                idMap[t.parentId].children.push(idMap[t.id]);
            } else {
                tree.push(idMap[t.id]);
            }
        });
        return tree;
    }

    // 팀원 테이블 컬럼
    const columns = [
        { title: "이름", dataIndex: "name", key: "name" },
        { title: "아이디", dataIndex: "username", key: "username" },
        { title: "이메일", dataIndex: "email", key: "email" },
        { title: "전화번호", dataIndex: "phone", key: "phone" },
    ];

    return (
        <div style={{ display: "flex", height: "100vh", background: "#f7f7f7" }}>
            {/* 좌측 트리 */}
            <div style={{ width: 350, background: "#fff", margin: 24, borderRadius: 8, boxShadow: "0 2px 8px #eee" }}>
                <h2 style={{ padding: 16, margin: 0 }}>팀 구성</h2>
                <div style={{ padding: 16 }}>
                    <Button type="primary" style={{ marginBottom: 8 }}>팀추가</Button>
                    <Tree
                        treeData={treeData}
                        defaultExpandAll
                        onSelect={keys => setSelectedTeamId(keys[0])}
                    />
                </div>
            </div>
            {/* 우측 팀원 리스트 */}
            <div style={{ flex: 1, background: "#fff", margin: 24, borderRadius: 8, boxShadow: "0 2px 8px #eee" }}>
                <div style={{ display: "flex", alignItems: "center", padding: 16 }}>
                    <h2 style={{ flex: 1, margin: 0 }}>팀원 리스트 : <span style={{ color: "#1abc9c" }}>{members.length}</span></h2>
                    <Button type="primary" style={{ marginRight: 8 }}>팀원추가</Button>
                    <Button style={{ marginRight: 8 }}>이동</Button>
                    <Button style={{ marginRight: 8 }}>삭제</Button>
                    <Input.Search
                        placeholder="팀, 팀원 검색"
                        onSearch={handleSearch}
                        style={{ width: 200 }}
                    />
                </div>
                <Table
                    columns={columns}
                    dataSource={members}
                    rowKey="id"
                    pagination={false}
                    style={{ margin: 16 }}
                />
            </div>
        </div>
    );
}

export default App;