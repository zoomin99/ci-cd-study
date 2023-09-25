// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.18;

contract Voting {
    struct Candidate {
        string name;
        uint8 voteCount;
    }

    struct Ballot {
        uint256 ballotId;
        string ballotName;
        Candidate[] candidateList;
    }

    Ballot[] public ballotList;

    function addBallot(string memory ballotName) public {

        uint id = ballotList.length + 1;
        Ballot storage b = ballotList.push();

        b.ballotId = id;
        b.ballotName = ballotName;
    }

    function getCandidateList(uint256 ballotId) public view returns (Candidate[] memory) {
        require(ballotExists(ballotId), "Ballot does not exist");
        Ballot storage ballot = ballotList[ballotId - 1];
        return ballot.candidateList;
    }

    function addCandidate(uint256 ballotId, string memory candidateName) public {
        require(ballotExists(ballotId), "Ballot does not exist");
        Ballot storage ballot = ballotList[ballotId - 1];

        require(!candidateExists(ballot, candidateName), "Candidate already exists");

        Candidate memory newCandidate = Candidate(candidateName, 0);
        ballot.candidateList.push(newCandidate);
    }

    function candidateExists(Ballot storage ballot, string memory candidate) private view returns (bool) {
        for (uint256 i = 0; i < ballot.candidateList.length; i++) {
            if (compareStrings(ballot.candidateList[i].name, candidate)) {
                return true;
            }
        }
        return false;
    }

    function voteForCandidate(uint256 ballotId, string memory candidate) public {
        require(ballotExists(ballotId), "Ballot does not exist");
        Ballot storage ballot = ballotList[ballotId - 1];
        uint256 candidateIndex = findCandidateIndex(ballot, candidate);
        require(candidateIndex != type(uint256).max, "Candidate does not exist");

        ballot.candidateList[candidateIndex].voteCount += 1;
    }

    function getVoteCount(uint256 ballotId, string memory candidate) public view returns (uint8) {
        require(ballotExists(ballotId), "Ballot does not exist");
        Ballot storage ballot = ballotList[ballotId - 1];
        uint256 candidateIndex = findCandidateIndex(ballot, candidate);
        require(candidateIndex != type(uint256).max, "Candidate does not exist");

        return ballot.candidateList[candidateIndex].voteCount;
    }

    function getBallotList() public view returns (Ballot[] memory) {
        return ballotList;
    }

    function ballotExists(uint256 ballotId) private view returns (bool) {
        for (uint256 i = 0; i < ballotList.length; i++) {
            if (ballotList[i].ballotId == ballotId) {
                return true;
            }
        }
        return false;
    }

    function findCandidateIndex(Ballot storage ballot, string memory candidate) private view returns (uint256) {
        for (uint256 i = 0; i < ballot.candidateList.length; i++) {
            if (compareStrings(ballot.candidateList[i].name, candidate)) {
                return i;
            }
        }
        return type(uint256).max;
    }

    function compareStrings(string memory a, string memory b) private pure returns (bool) {
        return keccak256(bytes(a)) == keccak256(bytes(b));
    }
}